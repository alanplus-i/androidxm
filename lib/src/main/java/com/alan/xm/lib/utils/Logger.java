package com.alan.xm.lib.utils;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alan.xm.lib.LApplication;
import com.alan.xm.lib.thread.ThreadExecutors;

import java.io.File;

/**
 * @author alan ye
 * created on  2022/11/18
 */
public class Logger {

    public static final String GLOBAL_TAG = "lib_log_tag";
    private static final int STACK_TRACE = 4;

    private static String logFile;

    static {
        logFile = SpUtils.getInstance().getLogName();
    }

    /**
     * Print logs in logcat
     *
     * @param message message array
     */
    public static void d(String... message) {
        if (isEnable()) {
            Log.d(GLOBAL_TAG, makeLogStringWithLongInfo(message));
        }
    }

    /**
     * Print logs in logcat
     *
     * @param message message int
     */
    public static void d(int message) {
        d(String.valueOf(message));
    }

    /**
     * Print logs in logcat
     *
     * @param message message boolean
     */
    public static void d(boolean message) {
        d(String.valueOf(message));
    }

    /**
     * Print logs in logcat
     *
     * @param message message boolean
     */
    public static void d(long message) {
        d(String.valueOf(message));
    }

    /**
     * Print logs in logcat with a tag
     *
     * @param tag     log tag
     * @param message message array
     */
    public static void d(@NonNull String tag, String message) {
        if (isEnable() && !TextUtils.isEmpty(tag) && checkParams(message)) {
            Log.d(tag, makeLogStringWithLongInfo(message));
        }
    }

    /**
     * Printing error logs
     *
     * @param e error info
     */
    public static void e(@NonNull Throwable e) {
        if (isEnable()) {
            Log.e(GLOBAL_TAG, Log.getStackTraceString(e));
        }
    }

    /**
     * print log to file
     *
     * @param message text content
     */
    public static void f(String message) {
        if (isEnable()) {
            ThreadExecutors.singleExecutor(() -> {
                String logs = FileUtils.getSystemPath("xm_logs");
                String path = logs + "/" + logFile;

                File file = new File(path);
                if (file.exists() && file.length() > 10 * 1024 * 1024) {
                    logFile = getNewLogName();
                    SpUtils.getInstance().setLogName(logFile);
                    path = logs + "/" + logFile;
                }
                String content = "[" +
                        TimeUtils.getCurrentTimeStr("yyyy-MM-dd hh:mm:ss") +
                        "] " +
                        message +
                        "\n";
                Logger.d(path);
                Logger.d(content);
                boolean b = FileUtils.write(content, path, true);
                d("file log recoding:" + b);
            });

        }
    }

    private static boolean checkParams(String... message) {
        return message != null && message.length > 0;
    }

    private static boolean isEnable() {
        return LApplication.app != null && LApplication.app.getConfig().isLog();
    }

    public static String getNewLogName() {
        return "xm_log_" + System.currentTimeMillis() + ".log";
    }

    private static String makeLogStringWithLongInfo(String... message) {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[STACK_TRACE];
        StringBuilder builder = new StringBuilder();
        appendTag(builder, stackTrace);
        appendTraceInfo(builder, stackTrace);
        for (String i : message) {
            builder.append(" ");
            builder.append(i);
        }
        return builder.toString();
    }

    private static void appendTag(StringBuilder builder, StackTraceElement stackTrace) {
        builder.append("[");
        builder.append(suppressFileExtension(stackTrace.getFileName()));
        builder.append("]" + " ");
    }

    private static String suppressFileExtension(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return "";
        }
        int extensionPosition = filename.lastIndexOf(".");
        if (extensionPosition > 0 && extensionPosition < filename.length()) {
            return filename.substring(0, extensionPosition);
        } else {
            return filename;
        }
    }

    private static void appendTraceInfo(StringBuilder builder, StackTraceElement stackTrace) {
        builder.append(stackTrace.getMethodName());
        builder.append(":");
        builder.append(stackTrace.getLineNumber());
        builder.append(" ");
    }

}
