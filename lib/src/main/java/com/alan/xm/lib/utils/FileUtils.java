package com.alan.xm.lib.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.alan.xm.lib.LApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public class FileUtils {

    /**
     * 写入文件
     *
     * @param content  文本内容
     * @param filePath 文件绝对路径
     * @param isAppend 是否追加
     * @return 是否写入成功
     */
    public static boolean write(String content, String filePath, boolean isAppend) {
        if (TextUtils.isEmpty(filePath)) {
            return true;
        }

        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
            if (!mkdirs) {
                Logger.d("Folder creation failure");
                return false;
            }
        }
        try {
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    Logger.d("File creation failure");
                    return false;
                }
            }
            FileWriter fileWriter = new FileWriter(file, isAppend);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(content);
            bw.close();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            Logger.e(e);
        }
        return false;
    }

    /**
     * 获取系统目录
     *
     * @return /data/data/package_name
     */
    public static String getSystemPath() {
        if (LApplication.app == null) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return LApplication.app.getDataDir().getPath();
        } else {
            return "/data/data/" + LApplication.app.getPackageName();
        }
    }

    /**
     * 获取系统自定义文件目录
     *
     * @param name 文件名 会生成app_文件名
     * @return /data/user/0/package_name/app_'name'
     */
    public static String getSystemPath(String name) {
        if (LApplication.app == null) {
            return "";
        }
        return LApplication.app.getDir(name, Context.MODE_PRIVATE).getPath();
    }


    /**
     * 获取系统文件目录
     *
     * @return /data/data/package_name/files
     */
    public static String getSystemFilePath() {
        if (LApplication.app == null) {
            return "";
        }
        return LApplication.app.getFilesDir().getPath();
    }

    /**
     * 获取系统缓存文件目录
     * 当设备上其他地方需要磁盘空间时，系统将自动删除该目录下的文件
     *
     * @return /data/data/package_name/cache
     */
    public static String getSystemCachePath() {
        if (LApplication.app == null) {
            return "";
        }
        return LApplication.app.getCacheDir().getPath();
    }

    /**
     * 获取外部存储目录
     *
     * @return /storage/sdcard/Android/data/package_name/'name'
     */
    public static String getExtFilePath(String name) {
        if (LApplication.app == null) {
            return "";
        }
        return LApplication.app.getExternalFilesDir(name).getPath();
    }

}
