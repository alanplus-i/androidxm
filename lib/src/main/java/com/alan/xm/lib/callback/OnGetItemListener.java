package com.alan.xm.lib.callback;

import java.io.UnsupportedEncodingException;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public interface OnGetItemListener<K, V> {

    void onGetItemListener(K k, V v);
}
