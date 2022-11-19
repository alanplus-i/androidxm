package com.alan.xm.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.alan.xm.lib.LApplication;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public class SpUtils {

    private SharedPreferences sp;
    private static SpUtils spUtils;

    private static final String KEY_LIB_XM_LOG_NAME = "key_lib_xm_log_name";
    private static final String KEY_LIB_XM_EXT_LOG_NAME = "key_lib_xm_ext_log_name";

    private SpUtils() {
        if (LApplication.app == null) {
            return;
        }
        sp = LApplication.app.getSharedPreferences("lib_xm", Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance() {
        if (null == spUtils || spUtils.sp == null) {
            synchronized (SpUtils.class) {
                if (null == spUtils || spUtils.sp == null) {
                    spUtils = new SpUtils();
                }
            }
        }
        return spUtils;
    }


    public String getLogName() {
        String logName = sp.getString(KEY_LIB_XM_LOG_NAME, "");
        if (TextUtils.isEmpty(logName)) {
            logName = Logger.getNewLogName();
            setLogName(logName);
        }
        return logName;
    }

    public String getExtLogName() {
        String logName = sp.getString(KEY_LIB_XM_EXT_LOG_NAME, "");
        if (TextUtils.isEmpty(logName)) {
            logName = Logger.getNewLogName();
            setExtLogName(logName);
        }
        return logName;
    }

    public void setLogName(String logName) {
        sp.edit().putString(KEY_LIB_XM_LOG_NAME, logName).commit();
    }

    public void setExtLogName(String logName) {
        sp.edit().putString(KEY_LIB_XM_EXT_LOG_NAME, logName).commit();
    }

}
