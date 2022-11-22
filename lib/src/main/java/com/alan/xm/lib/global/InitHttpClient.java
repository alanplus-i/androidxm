package com.alan.xm.lib.global;

import okhttp3.OkHttpClient;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public interface InitHttpClient {

    void initOkHttpClient(OkHttpClient.Builder builder);
}
