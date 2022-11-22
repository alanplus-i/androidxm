package com.alan.xm.lib.http;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alan.xm.lib.exception.XException;
import com.alan.xm.lib.exception.XExceptionFactory;
import com.alan.xm.lib.utils.Logger;
import com.alan.xm.lib.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author alan ye
 * created on  2022/10/20
 */
public class XParams {

    protected final HashMap<String, String> headers = new HashMap<>();
    protected static final String CHARSET_NAME = "UTF-8";
    protected final HashMap<String, String> params = new HashMap<>();
    protected final JSONObject object = new JSONObject();
    private final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    protected String url;


    /**
     * @param isEncoding is encoding
     * @return a string of params map
     * @throws XException XException
     */
    private String toString(boolean isEncoding) throws XException {
        StringBuilder sb = new StringBuilder();
        AtomicReference<XException> xException = new AtomicReference<>();
        Utils.onMapItemListener(params, (key, value) -> {
            if (value != null) {
                if (isEncoding) {
                    try {
                        value = URLEncoder.encode(value, CHARSET_NAME);
                    } catch (UnsupportedEncodingException e) {
                        xException.set(XExceptionFactory.createEnCodingError());
                    }
                }
                sb.append(key);
                sb.append('=');
                sb.append(value);
                sb.append('&');
            }
        });
        if (xException.get() != null) {
            throw XExceptionFactory.createEnCodingError();
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private void addHeader(@NonNull Request.Builder builder) {
        Utils.onMapItemListener(headers, builder::addHeader);
    }

    public Request.Builder toGetBuilder() throws XException {
        String u = url + "?" + toString(true);
        isValidUrl(u);
        Request.Builder builder = new Request.Builder();
        addHeader(builder);
        builder.url(u);
        return builder;
    }

    public Request.Builder toDelBuilder() throws XException {
        return toGetBuilder().delete();
    }

    public Request.Builder toPostBuilder() throws XException {
        isValidUrl(url);
        Request.Builder builder = new Request.Builder();
        addHeader(builder);
        builder.url(url);
        Logger.d(object.toString());
        builder.post(RequestBody.create(mediaType, object.toString()));
        return builder;
    }

    public Request.Builder toPutBuilder() throws XException {
        isValidUrl(url);
        Request.Builder builder = new Request.Builder();
        addHeader(builder);
        builder.url(url);
        builder.put(RequestBody.create(mediaType, object.toString()));
        return builder;
    }

    private void isValidUrl(String url) throws XException {
        Logger.d("url:" + url);
        if (TextUtils.isEmpty(url)) {
            throw XExceptionFactory.createUrlInvalidError();
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw XExceptionFactory.createUrlInvalidError();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final XParams params;

        private Builder() {
            params = new XParams();
        }

        public Builder addParam(String key, String value) {
            params.params.put(key, value);
            return this;
        }

        public Builder addParam(String key, int value) {
            params.params.put(key, String.valueOf(value));
            return this;
        }

        public Builder addParam(String key, boolean value) {
            params.params.put(key, String.valueOf(value));
            return this;
        }

        public Builder addParam(String key, long value) {
            params.params.put(key, String.valueOf(value));
            return this;
        }

        public Builder addParam(String key, double value) {
            params.params.put(key, String.valueOf(value));
            return this;
        }

        public Builder addParams(HashMap<String, String> map) {
            if (map != null && map.size() > 0) {
                params.params.putAll(map);
            }
            return this;
        }

        public Builder addJsonParam(String key, double value) {
            try {
                params.object.put(key, value);
            } catch (JSONException e) {
                Logger.e(e);
            }
            return this;
        }

        public Builder addJsonParam(String key, int value) {
            try {
                params.object.put(key, value);
            } catch (JSONException e) {
                Logger.e(e);
            }
            return this;
        }

        public Builder addJsonParam(String key, String value) {
            try {
                params.object.put(key, value);
            } catch (JSONException e) {
                Logger.e(e);
            }
            return this;
        }

        public Builder addJsonParam(String key, boolean value) {
            try {
                params.object.put(key, value);
            } catch (JSONException e) {
                Logger.e(e);
            }
            return this;
        }

        public Builder addJsonParam(String key, JSONObject value) {
            try {
                params.object.put(key, value);
            } catch (JSONException e) {
                Logger.e(e);
            }
            return this;
        }

        public Builder addHeader(String key, String value) {
            params.headers.put(key, value);
            return this;
        }

        public Builder addHeaders(HashMap<String, String> map) {
            if (map != null && map.size() > 0) {
                params.headers.putAll(map);
            }
            return this;
        }

        public Builder addJsonParam(String key, JSONArray value) {
            try {
                params.object.put(key, value);
            } catch (JSONException e) {
                Logger.e(e);
            }
            return this;
        }

        public Builder url(String url) {
            params.url = url;
            return this;
        }

        public XParams build() {
            return params;
        }
    }
}
