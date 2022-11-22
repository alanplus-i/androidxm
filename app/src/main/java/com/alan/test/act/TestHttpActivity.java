package com.alan.test.act;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alan.test.R;
import com.alan.xm.lib.exception.XException;
import com.alan.xm.lib.http.XHttpClient;
import com.alan.xm.lib.http.XParams;
import com.alan.xm.lib.http.callback.SimpleCallback;
import com.alan.xm.lib.utils.Logger;

/**
 * @author alan ye
 * created on  2022/11/22
 */
public class TestHttpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemView("test GET").setOnClickListener(view -> testGet());
        addItemView("test POST").setOnClickListener(view -> testPost());
        addItemView("test PUT").setOnClickListener(view -> testPut());
        addItemView("test DELETE").setOnClickListener(view -> testDelete());
    }

    private void testGet(){
        XParams.Builder builder = XParams.builder();
        builder.addParam("test", "abc");
        builder.addParam("key1", "您好");
        builder.url("http://192.168.20.42:8076/test");
        XHttpClient.getInstance().get(builder.build(), new SimpleCallback() {
            @Override
            public void failure(XException e) {
                Logger.d(e.code);
                Logger.d(e.message);
            }

            @Override
            public void success(String content) {
                Logger.d("thread:" + Thread.currentThread().getName());
                Logger.d(content);
            }
        });
    }

    private void testPost(){
        XParams.Builder builder = XParams.builder();
        builder.addJsonParam("test", "abc");
        builder.addJsonParam("key1", "您好");
        builder.url("http://192.168.20.42:8076/test");
        XHttpClient.getInstance().post(builder.build(), new SimpleCallback() {
            @Override
            public void failure(XException e) {
                Logger.d(e.code);
                Logger.d(e.message);
            }

            @Override
            public void success(String content) {
                Logger.d("thread:" + Thread.currentThread().getName());
                Logger.d(content);
            }
        });
    }

    private void testPut(){
        XParams.Builder builder = XParams.builder();
        builder.addJsonParam("test", "abc");
        builder.addJsonParam("key1", "您好");
        builder.url("http://192.168.20.42:8076/test");
        XHttpClient.getInstance().put(builder.build(), new SimpleCallback() {
            @Override
            public void failure(XException e) {
                Logger.d(e.code);
                Logger.d(e.message);
            }

            @Override
            public void success(String content) {
                Logger.d("thread:" + Thread.currentThread().getName());
                Logger.d(content);
            }
        });
    }

    private void testDelete(){
        XParams.Builder builder = XParams.builder();
        builder.addJsonParam("test", "abc");
        builder.addJsonParam("key1", "您好");
        builder.url("http://192.168.20.42:8076/test");
        XHttpClient.getInstance().delete(builder.build(), new SimpleCallback() {
            @Override
            public void failure(XException e) {
                Logger.d(e.code);
                Logger.d(e.message);
            }

            @Override
            public void success(String content) {
                Logger.d("thread:" + Thread.currentThread().getName());
                Logger.d(content);
            }
        });
    }
}
