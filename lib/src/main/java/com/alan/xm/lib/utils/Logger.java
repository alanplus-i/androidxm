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
    private static String extLogFile;

    static {
        logFile = SpUtils.getInstance().getLogName();
        extLogFile = SpUtils.getInstance().getExtLogName();
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
     * print log to file<br>
     * 保存路径 /data/user/0/package_name/app_xm_logs
     *
     * @param message text content
     */
    public static void f(String message) {
        if (isEnable()) {
            ThreadExecutors.singleExecutor(() -> {
                String logs = FileUtils.getSystemPath("xm_logs");
                String name = getLogFile(logs, logFile);
                if (!TextUtils.isEmpty(name) && !name.equals(logFile)) {
                    SpUtils.getInstance().setLogName(logFile);
                    logFile = name;
                }
                String path = logs + "/" + logFile;
                writeFile(message, path, true);
            });

        }
    }


    /**
     * print log to external file<br>
     * 保存路径 /storage/sdcard/Android/data/package_name/xm_logs
     *
     * @param message text content
     */
    public static void ext(String message) {
        if (isEnable()) {
            ThreadExecutors.singleExecutor(() -> {
                String logs = FileUtils.getExtFilePath("xm_logs");
                String name = getLogFile(logs, extLogFile);
                if (!TextUtils.isEmpty(name) && !name.equals(extLogFile)) {
                    extLogFile = name;
                    SpUtils.getInstance().setExtLogName(extLogFile);
                }
                String path = logs + "/" + extLogFile;
                writeFile(message, path, true);
            });

        }
    }

    private static String getLogFile(String path, String name) {
        String filePath = path + "/" + name;
        File file = new File(filePath);
        if (file.exists() && file.length() > 10 * 1024 * 1024) {
            Logger.d(file.length());
            name = getNewLogName();

        }
        return name;
    }

    private static void writeFile(String message, String path, boolean isAppend) {
        String content = "[" +
                TimeUtils.getCurrentTimeStr("yyyy-MM-dd HH:mm:ss") +
                "] " +
                message +
                "\n";
        Logger.d(path);
        Logger.d(content);
        boolean b = FileUtils.write(content, path, isAppend);
        d("file log recoding:" + b);
    }

    private static boolean checkParams(String... message) {
        return message != null && message.length > 0;
    }

    private static boolean isEnable() {
        return LApplication.getConfig() != null && LApplication.getConfig().isLog();
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
