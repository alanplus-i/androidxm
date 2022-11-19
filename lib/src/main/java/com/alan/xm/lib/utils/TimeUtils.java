package com.alan.xm.lib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public class TimeUtils {

    public static String getCurrentTimeStr(String formatStr) {
        return getTimeStr(System.currentTimeMillis(), formatStr);
    }

    public static String getTimeStr(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
        Date date = new Date();
        date.setTime(time);
        return format.format(date);
    }

}
