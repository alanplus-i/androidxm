package com.alan.xm.lib;

import android.app.Application;

import com.alan.xm.lib.global.LibConfig;

/**
 * @author alan ye
 * created on  2022/11/18
 */
public class LApplication extends Application {

    public static LApplication app;

    private LibConfig config;

    @Override
    public void onCreate() {
        super.onCreate();
        LApplication.app = this;
    }

    protected void setConfig(LibConfig config) {
        this.config = config;
    }

    public static LibConfig getConfig() {
        if (app != null) {
            return null == app.config ? LibConfig.builder().build() : app.config;
        }
        return null;
    }
}
