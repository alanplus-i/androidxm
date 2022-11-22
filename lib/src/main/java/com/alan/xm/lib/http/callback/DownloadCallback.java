package com.alan.xm.lib.http.callback;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.alan.xm.lib.exception.XExceptionFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author alan ye
 * created on  2022/9/28
 */
public abstract class DownloadCallback extends SimpleCallback {

    private final String filePath;
    private final Handler handler;


    public DownloadCallback(String filePath) {
        this.filePath = filePath;
        handler = new Handler(Looper.getMainLooper());
    }

    public abstract void process(long process, long total);

    public abstract void complete(File file);

    public File handlerFile() throws IOException {
        File destFile = new File(filePath);
        if (destFile.exists()) {
            boolean delete = destFile.delete();
        } else {
            boolean newFile = destFile.createNewFile();
        }
        return destFile;
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

        if (response.body() == null) {
            onFailureThrowable(XExceptionFactory.createHttpBodyNullError());
            return;
        }
        try {
            byte[] buf = new byte[2048];
            int len = 0;
            File destFile = handlerFile();
            InputStream is = response.body().byteStream();
            long total = response.body().contentLength();
            FileOutputStream fos = new FileOutputStream(destFile);
            long sum = 0;
            long postTime = System.currentTimeMillis();
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                //下载中更新进度条
                postTime = postProcess(postTime, progress, total);
            }
            handler.post(() -> complete(destFile));
            //下载完成
            fos.flush();
            is.close();
        } catch (Exception e) {
            onFailureThrowable(e);
        }
    }

    private long postProcess(long time, long p, long total) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - time > 300) {
            handler.post(() -> {
                process(p, total);
            });
            return currentTime;
        }
        return time;
    }
}
