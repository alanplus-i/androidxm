package com.alan.xm.lib.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alan.xm.lib.exception.XException;
import com.alan.xm.lib.exception.XExceptionFactory;
import com.alan.xm.lib.utils.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author alan ye
 * created on  2022/9/16
 */
public abstract class SimpleTCallback<T> extends SimpleCallback {

    @Override
    public void success(String content) {
        T resolver = resolver(content);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> onSuccess(resolver));
    }

    public abstract T resolver(String content);

    public abstract void onSuccess(T t);
}
