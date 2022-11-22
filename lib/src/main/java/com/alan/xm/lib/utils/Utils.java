package com.alan.xm.lib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.alan.xm.lib.callback.OnGetItemListener;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public class Utils {

    /**
     * Traversal map
     *
     * @param map      map
     * @param listener listener
     * @param <K>      K
     * @param <V>      V
     */
    public static <K, V> void onMapItemListener(Map<K, V> map, OnGetItemListener<K, V> listener) {
        if (null == map || listener == null || map.size() == 0) {
            return;
        }
        Set<K> keys = map.keySet();
        for (K key : keys) {
            V v = map.get(key);
            listener.onGetItemListener(key, v);
        }
    }

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率 px(像素) 转 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕分辨
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    /**
     * 返回原尺寸的DisplayMetrics android 4.0默认会减掉通知栏部分
     *
     * @param context Context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric;
    }


}
