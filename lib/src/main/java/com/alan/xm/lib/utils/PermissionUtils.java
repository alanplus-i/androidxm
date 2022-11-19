package com.alan.xm.lib.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.alan.xm.lib.LApplication;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public class PermissionUtils {


    public static boolean hasWritingSdcardPermission() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(LApplication.app, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
    }


}
