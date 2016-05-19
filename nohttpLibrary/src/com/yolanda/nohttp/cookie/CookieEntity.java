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

import java.io.Serializable;
import java.net.HttpCookie;
import java.net.URI;

import com.yolanda.nohttp.db.DBId;
import com.yolanda.nohttp.tools.HttpDateTime;

import android.text.TextUtils;

/**
 * <p>Cookie entity.</p>
 * Created in Dec 17, 2015 7:21:16 PM.
 *
 * @author YOLANDA;
 */
class CookieEntity implements DBId, Serializable {

    private static final long serialVersionUID = 6374381323722046732L;

    /**
     * Max expiry: 100 years.
     */
    private long id = -1;
    private String uri; // cookie add by this uri.
    private String name;
    private String value;
    private String comment;
    private String commentURL;
    private boolean discard;
    private String domain;
    private long expiry;
    private String path;
    private String portList;
    private boolean secure;
    private int version = 1;

    public CookieEntity() {
    }

    /**
     * @param uri    cookie corresponding uri.
     * @param cookie cookie.
     */
    public CookieEntity(URI uri, HttpCookie cookie) {
        this.uri = uri == null ? null : uri.toString();
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.comment = cookie.getComment();
        this.commentURL = cookie.getCommentURL();
        this.discard = cookie.getDiscard();
        this.domain = cookie.getDomain();
        long maxAge = cookie.getMaxAge();
        if (maxAge > 0L) { // session, temp cookie
            this.expiry = (maxAge * 1000L) + System.currentTimeMillis();
            // 溢出
            if (this.expiry < 0)
                this.expiry = HttpDateTime.getMaxExpiryMillis();
        } else {
            this.expiry = -1L;
        }
        this.path = cookie.getPath();
        if (!TextUtils.isEmpty(path) && path.length() > 1 && path.endsWith("/")) {
            this.path = path.substring(0, path.length() - 1);
        }
        this.portList = cookie.getPortlist();
        this.secure = cookie.getSecure();
        this.version = cookie.getVersion();
    }

    public HttpCookie toHttpCookie() {
        HttpCookie cookie = new HttpCookie(name, value);
        cookie.setComment(comment);
        cookie.setCommentURL(commentURL);
        cookie.setDiscard(discard);
        cookie.setDomain(domain);
        cookie.setMaxAge((expiry - System.currentTimeMillis()) / 1000L);
        cookie.setPath(path);
        cookie.setPortlist(portList);
        cookie.setSecure(secure);
        cookie.setVersion(version);
        return cookie;
    }

    /**
     * @return the id.
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the uri.
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the commentURL.
     */
    public String getCommentURL() {
        return commentURL;
    }

    /**
     * @param commentURL the commentURL to set.
     */
    public void setCommentURL(String commentURL) {
        this.commentURL = commentURL;
    }

    /**
     * @return the discard.
     */
    public boolean isDiscard() {
        return discard;
    }

    /**
     * @param discard the discard to set.
     */
    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    /**
     * @return the domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the expiry.
     */
    public long getExpiry() {
        return expiry;
    }

    /**
     * @param expiry the expiry to set.
     */
    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    /**
     * @return the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the portList.
     */
    public String getPortList() {
        return portList;
    }

    /**
     * @param portList the portList to set.
     */
    public void setPortList(String portList) {
        this.portList = portList;
    }

    /**
     * @return the secure.
     */
    public boolean isSecure() {
        return secure;
    }

    /**
     * @param secure the secure to set.
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    /**
     * @return the version.
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set.
     */
    public void setVersion(int version) {
        this.version = version;
    }

}
