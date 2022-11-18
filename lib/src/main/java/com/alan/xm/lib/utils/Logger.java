package com.alan.xm.lib.utils;

import com.alan.xm.lib.LApplication;

/**
 * @author alan ye
 * created on  2022/11/18
 */
public class Logger {

    private boolean isEnable() {
        return LApplication.app != null && LApplication.app.getConfig().isLog();
    }

}
