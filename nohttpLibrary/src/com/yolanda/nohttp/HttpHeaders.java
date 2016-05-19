/**
 * Copyright © YOLANDA. All Rights Reserved
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yolanda.nohttp;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.HttpCookie;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yolanda.nohttp.tools.HttpDateTime;
import com.yolanda.nohttp.tools.LinkedMultiValueMap;

import android.text.TextUtils;

/**
 * <p>{@link Headers} The default implementation.</p>
 * Created in Jan 10, 2016 2:37:06 PM.
 *
 * @author YOLANDA;
 */
public class HttpHeaders extends LinkedMultiValueMap<String, String> implements Headers {

    public HttpHeaders() {
    }

    @Override
    public void addAll(Headers headers) {
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                add(key, headers.getValues(key));
            }
        }
    }

    @Override
    public void setAll(Headers headers) {
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                set(key, headers.getValues(key));
            }
        }
    }

    @Override
    public void addCookie(URI uri, CookieHandler cookieHandler) {
        try {
            Map<String, List<String>> diskCookies = cookieHandler.get(uri, new HashMap<String, List<String>>());
            for (Map.Entry<String, List<String>> entry : diskCookies.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if ((Headers.HEAD_KEY_COOKIE.equalsIgnoreCase(key) || Headers.HEAD_KEY_COOKIE2.equalsIgnoreCase(key))) {
                    add(key, TextUtils.join("; ", value));
                }
            }
        } catch (IOException e) {
            Logger.e(e);
        }
    }

    @Override
    public void setJSONString(String jsonString) throws JSONException {
        mSource.clear();
        JSONObject jsonObject = new JSONObject(jsonString);
        Iterator<String> keySet = jsonObject.keys();
        while (keySet.hasNext()) {
            String key = keySet.next();
            String value = jsonObject.optString(key);
            JSONArray values = new JSONArray(value);
            if (values != null)
                for (int i = 0; i < values.length(); i++)
                    add(key, values.optString(i));
        }
    }

    @Override
    public final String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        Set<Map.Entry<String, List<String>>> entrySet = mSource.entrySet();
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            JSONArray value = new JSONArray(values);
            try {
                jsonObject.put(key, value);
            } catch (JSONException e) {
                Logger.w(e);
            }
        }

        return jsonObject.toString();
    }

    @Override
    public Map<String, String> toRequestHeaders() {
        Map<String, String> singleMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, List<String>> entry : mSource.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            String trueValue = TextUtils.join("; ", value);
            singleMap.put(key, trueValue);
        }
        return singleMap;
    }

    @Override
    public Map<String, List<String>> toResponseHeaders() {
        return mSource;
    }

    @Override
    public List<HttpCookie> getCookies() {
        List<HttpCookie> cookies = new ArrayList<HttpCookie>();
        for (String key : keySet()) {
            if (key.equalsIgnoreCase(HEAD_KEY_SET_COOKIE) || key.equalsIgnoreCase(HEAD_KEY_SET_COOKIE2)) {
                List<String> cookieValues = getValues(key);
                for (String cookieStr : cookieValues) {
                    for (HttpCookie cookie : HttpCookie.parse(cookieStr))
                        cookies.add(cookie);
                }
            }
        }
        return cookies;
    }

    @Override
    public String getCacheControl() {
        // first http1.1, second http1.0
        List<String> cacheControls = getValues(HEAD_KEY_CACHE_CONTROL);
        if (cacheControls == null)
            cacheControls = getValues(HEAD_KEY_PRAGMA);
        if (cacheControls == null)
            cacheControls = new ArrayList<String>();
        return TextUtils.join(",", cacheControls);
    }

    @Override
    public String getContentEncoding() {
        return getValue(HEAD_KEY_CONTENT_ENCODING, 0);
    }

    @Override
    public int getContentLength() {
        String contentLength = getValue(HEAD_KEY_CONTENT_LENGTH, 0);
        try {
            return Integer.parseInt(contentLength);
        } catch (Exception e) {
        }
        return 0;
    }

    @Override
    public int getResponseCode() {
        String responseCode = getValue(HEAD_KEY_RESPONSE_CODE, 0);
        int code = 0;
        try {
            code = Integer.parseInt(responseCode);
        } catch (Exception e) {
        }
        return code;
    }

    @Override
    public String getResponseMessage() {
        return getValue(HEAD_KEY_RESPONSE_MESSAGE, 0);
    }

    @Override
    public String getContentType() {
        return getValue(HEAD_KEY_CONTENT_TYPE, 0);
    }

    @Override
    public long getDate() {
        return getDateField(HEAD_KEY_DATE);
    }

    @Override
    public String getETag() {
        return getValue(HEAD_KEY_E_TAG, 0);
    }

    @Override
    public long getExpiration() {
        return getDateField(HEAD_KEY_EXPIRES);
    }

    @Override
    public long getLastModified() {
        return getDateField(HEAD_KEY_LAST_MODIFIED);
    }

    @Override
    public String getLocation() {
        return getValue(HEAD_KEY_LOCATION, 0);
    }

    /**
     * <p>Returns the date value in milliseconds since 1970.1.1, 00:00h corresponding to the header field field. The
     * defaultValue will be returned if no such field can be found in the response header.</p>
     *
     * @param key the header field name.
     * @return the header field represented in milliseconds since January 1, 1970 GMT.
     */
    private long getDateField(String key) {
        String value = getValue(key, 0);
        if (value != null)
            try {
                return HttpDateTime.parseGMTToMillis(value);
            } catch (ParseException e) {
                Logger.w(e);
            }
        return 0;
    }

    @Override
    public String toString() {
        return toJSONString();
    }

}
