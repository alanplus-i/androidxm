package com.alan.xm.lib.http;

import androidx.annotation.NonNull;

import com.alan.xm.lib.LApplication;
import com.alan.xm.lib.http.callback.DownloadCallback;
import com.alan.xm.lib.http.callback.SimpleCallback;
import com.alan.xm.lib.utils.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XHttpClient {

    private static XHttpClient client;
    private final OkHttpClient okHttpClient;
    private static final int TIME_OUT = 30;

    private static final boolean isMock = true;

    private final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    private XHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        if (LApplication.getConfig() != null && LApplication.getConfig().getInitHttpClient() != null) {
            LApplication.getConfig().getInitHttpClient().initOkHttpClient(builder);
        }
        okHttpClient = builder.build();
    }

    public static XHttpClient getInstance() {
        if (null == client) {
            synchronized (XHttpClient.class) {
                if (null == client) {
                    client = new XHttpClient();
                }
            }
        }
        return client;
    }


    /**
     * asynchronous get request
     *
     * @param params   params
     * @param callback callback
     */
    public void get(@NonNull XParams params, @NonNull SimpleCallback callback) {
        try {
            Request request = params.toGetBuilder().build();
            okHttpClient.newCall(request).enqueue(callback);
        } catch (Throwable e) {
            onError(e, callback);
        }
    }

    /**
     * asynchronous delete request
     *
     * @param params   params
     * @param callback callback
     */
    public void delete(@NonNull XParams params, SimpleCallback callback) {
        try {
            Request request = params.toDelBuilder().build();
            okHttpClient.newCall(request).enqueue(callback);
        } catch (Throwable e) {
            onError(e, callback);
        }
    }

    private void onError(Throwable e, @NonNull SimpleCallback callback) {
        Logger.e(e);
        callback.onFailureThrowable(e);
    }

    /**
     * asynchronous download request
     *
     * @param params   params
     * @param callback callback
     */
    public void download(@NonNull XParams params, DownloadCallback callback) {
        try {
            Request.Builder builder = params.toGetBuilder();
            Request request = builder.build();
            okHttpClient.newCall(request).enqueue(callback);
        } catch (Throwable e) {
            Logger.e(e);
            callback.onFailureThrowable(e);
        }
    }

    /**
     * asynchronous put request
     *
     * @param params   params
     * @param callback callback
     */
    public void put(@NonNull XParams params, SimpleCallback callback) {
        try {
            Request request = params.toPutBuilder().build();
            okHttpClient.newCall(request).enqueue(callback);
        } catch (Throwable e) {
            onError(e, callback);
        }
    }

    /**
     * asynchronous post request
     *
     * @param params   params
     * @param callback callback
     */
    public void post(@NonNull XParams params, SimpleCallback callback) {
        try {
            Request request = params.toPostBuilder().build();
            okHttpClient.newCall(request).enqueue(callback);
        } catch (Exception e) {
            onError(e, callback);
        }
    }

    /**
     * POST Method of synchronization
     *
     * @param params params
     * @return string
     */
    public String post(@NonNull XParams params) {
        try {
            Request request = params.toPostBuilder().build();
            Response response = okHttpClient.newCall(request).execute();
            if (null != response.body()) {
                String string = response.body().string();
                Logger.d(string);
                return string;
            }
        } catch (Exception e) {
            Logger.e(e);
        }
        return "";
    }

    /**
     * GET Method of synchronization
     *
     * @param params params
     * @return string
     */
    public String get(@NonNull XParams params) {
        try {
            Request request = params.toGetBuilder().build();
            Response response = okHttpClient.newCall(request).execute();
            if (null != response.body()) {
                String string = response.body().string();
                Logger.d(string);
                return string;
            }
        } catch (Exception e) {
            Logger.e(e);
        }
        return "";
    }


    /**
     * PUT Method of synchronization
     *
     * @param params params
     * @return string
     */
    public String put(@NonNull XParams params) {
        try {
            Request request = params.toPutBuilder().build();
            Response response = okHttpClient.newCall(request).execute();
            if (null != response.body()) {
                String string = response.body().string();
                Logger.d(string);
                return string;
            }
        } catch (Exception e) {
            Logger.e(e);
        }
        return "";
    }

    /**
     * DELETE Method of synchronization
     *
     * @param params params
     * @return string
     */
    public String delete(@NonNull XParams params) {
        try {
            Request request = params.toDelBuilder().build();
            Response response = okHttpClient.newCall(request).execute();
            if (null != response.body()) {
                String string = response.body().string();
                Logger.d(string);
                return string;
            }
        } catch (Exception e) {
            Logger.e(e);
        }
        return "";
    }
}
