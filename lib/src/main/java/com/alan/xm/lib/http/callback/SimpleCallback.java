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
public abstract class SimpleCallback implements Callback {


    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Logger.e(e);
        onFailureThrowable(XExceptionFactory.createHttpIoError(e));
    }

    public void onFailureThrowable(Throwable e) {
        final XException exception;
        if (e instanceof XException) {
            exception = (XException) e;
        } else {
            exception = XExceptionFactory.createUnknownError(e);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> failure(exception));
    }


    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.body() != null) {
            String content = response.body().string();
            Logger.d(content);
            if (TextUtils.isEmpty(content)) {
                onFailureThrowable(XExceptionFactory.createHttpBodyNullError());
            } else {
                success(content);
            }
        } else {
            Logger.d("http error");
            onFailureThrowable(XExceptionFactory.createHttpBodyNullError());
        }
    }

    public abstract void failure(XException e);

    public abstract void success(String content);
}
