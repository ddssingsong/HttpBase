/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yolanda.nohttp.cookie;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.yolanda.nohttp.db.DBManager;
import com.yolanda.nohttp.db.Field;
import com.yolanda.nohttp.db.Where;
import com.yolanda.nohttp.db.Where.Options;

import android.text.TextUtils;

/**
 * Created in Dec 17, 2015 7:20:52 PM.
 *
 * @author YOLANDA;
 */
public enum DiskCookieStore implements CookieStore {

    INSTANCE;

    /**
     * Cookie max count in disk.
     */
    private final static int MAX_COOKIE_SIZE = 8888;
    /**
     * Database sync lock.
     */
    private Lock mLock;
    /**
     * Database Manager.
     */
    private DBManager<CookieEntity> mManager;
    /**
     * When delete expired cookies for the first time to delete temporary cookies.
     */
    private volatile boolean firstDeleteExpiry = true;
    /**
     * When Add and remove cookie notify.
     */
    private CookieStoreListener mCookieStoreListener;

    DiskCookieStore() {
        mLock = new ReentrantLock();
        mManager = CookieDiskManager.getInstance();
    }

    /**
     * The callback when adding and deleting cookies.
     *
     * @param mCookieStoreListener {@link CookieStoreListener}
     */
    public void setCookieStoreListener(CookieStoreListener mCookieStoreListener) {
        this.mCookieStoreListener = mCookieStoreListener;
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        if (uri != null && cookie != null) {
            mLock.lock();
            try {
                uri = getEffectiveURI(uri);
                if (mCookieStoreListener != null)
                    mCookieStoreListener.onSaveCookie(uri, cookie);
                mManager.replace(new CookieEntity(uri, cookie));
                trimSize();
            } finally {
                mLock.unlock();
            }
        }
    }

    @Override
    public List<HttpCookie> get(URI uri) {
        if (uri == null)
            return Collections.emptyList();
        mLock.lock();
        try {
            uri = getEffectiveURI(uri);
            deleteExpiryCookies();
            Where where = new Where();
            String host = uri.getHost();
            if (!TextUtils.isEmpty(host)) {
                Where subWhere = new Where(CookieDisk.DOMAIN, Options.EQUAL, host);
                int lastDot = host.lastIndexOf(".");
                if (lastDot > 1) {
                    lastDot = host.lastIndexOf(".", lastDot - 1);
                    if (lastDot > 0) {
                        String domain = host.substring(lastDot, host.length());
                        if (!TextUtils.isEmpty(domain))
                            subWhere.or(CookieDisk.DOMAIN, Options.EQUAL, domain).bracket();
                    }
                }
                where.set(subWhere.get());
            }

            String path = uri.getPath();
            if (!TextUtils.isEmpty(path)) {
                Where subWhere = new Where(CookieDisk.PATH, Options.EQUAL, path).or(CookieDisk.PATH, Options.EQUAL, "/").orNull(CookieDisk.PATH);
                int lastSplit = path.lastIndexOf("/");
                while (lastSplit > 0) {
                    path = path.substring(0, lastSplit);
                    subWhere.or(CookieDisk.PATH, Options.EQUAL, path);
                    lastSplit = path.lastIndexOf("/");
                }
                subWhere.bracket();
                where.and(subWhere);
            }

            where.or(CookieDisk.URI, Options.EQUAL, uri.toString());

            List<CookieEntity> cookieList = mManager.get(Field.ALL, where.get(), null, null, null);
            List<HttpCookie> returnedCookies = new ArrayList<HttpCookie>();
            for (CookieEntity cookieEntity : cookieList)
                returnedCookies.add(cookieEntity.toHttpCookie());
            return returnedCookies;
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public List<HttpCookie> getCookies() {
        mLock.lock();
        try {
            List<HttpCookie> rt = new ArrayList<HttpCookie>();
            deleteExpiryCookies();
            List<CookieEntity> cookieEntityList = mManager.getAll();
            for (CookieEntity cookieEntity : cookieEntityList)
                rt.add(cookieEntity.toHttpCookie());
            return rt;
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public List<URI> getURIs() {
        mLock.lock();
        try {
            List<URI> uris = new ArrayList<URI>();
            List<CookieEntity> uriList = mManager.getAll(CookieDisk.URI);
            for (CookieEntity cookie : uriList) {
                String uri = cookie.getUri();
                if (!TextUtils.isEmpty(uri))
                    try {
                        uris.add(new URI(uri));
                    } catch (Throwable e) {
                        e.printStackTrace();
                        StringBuilder where = new StringBuilder(CookieDisk.URI).append('=').append(uri);
                        mManager.delete(where.toString());
                    }
            }
            return uris;
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public boolean remove(URI uri, HttpCookie httpCookie) {
        if (httpCookie == null)
            return true;
        mLock.lock();
        try {
            if (mCookieStoreListener != null)
                mCookieStoreListener.onRemoveCookie(uri, httpCookie);
            Where where = new Where(CookieDisk.NAME, Options.EQUAL, httpCookie.getName());

            String domain = httpCookie.getDomain();
            if (!TextUtils.isEmpty(domain))
                where.and(CookieDisk.DOMAIN, Options.EQUAL, domain);

            String path = httpCookie.getPath();
            if (!TextUtils.isEmpty(path)) {
                if (path.length() > 1 && path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }
                where.and(CookieDisk.PATH, Options.EQUAL, path);
            }
            return mManager.delete(where.toString());
        } finally {
            mLock.unlock();
        }
    }

    @Override
    public boolean removeAll() {
        mLock.lock();
        try {
            return mManager.deleteAll();
        } finally {
            mLock.unlock();
        }
    }

    /**
     * Delete all expired cookies.
     */
    private void deleteExpiryCookies() {
        if (firstDeleteExpiry) {
            firstDeleteExpiry = false;
            deleteTempCookie();
        }
        Where where = new Where(CookieDisk.EXPIRY, Options.THAN_SMALL, System.currentTimeMillis()).and(CookieDisk.EXPIRY, Options.NO_EQUAL, -1L);
        mManager.delete(where.get());
    }

    /**
     * Delete all temp cookie.
     */
    private void deleteTempCookie() {
        Where where = new Where(CookieDisk.EXPIRY, Options.EQUAL, -1L);
        mManager.delete(where.get());
    }

    /**
     * Trim the Cookie list.
     */
    private void trimSize() {
        int count = mManager.count();
        if (count > MAX_COOKIE_SIZE + 10) {
            List<CookieEntity> rmList = mManager.get(Field.ALL, null, null, Integer.toString(count - MAX_COOKIE_SIZE), null);
            if (rmList != null)
                mManager.delete(rmList);
        }
    }

    /**
     * Get effective URI.
     *
     * @param uri cookie corresponding uri.
     */
    private URI getEffectiveURI(final URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI("http", uri.getHost(), uri.getPath(), null, null);
        } catch (URISyntaxException e) {
            effectiveURI = uri;
        }
        return effectiveURI;
    }
}