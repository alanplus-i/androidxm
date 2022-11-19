package com.alan.xm.lib.thread;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author alan ye
 * created on  2022/11/19
 */
public class ThreadExecutors {

    private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static void singleExecutor(@NonNull Runnable runnable) {
        singleThreadExecutor.execute(runnable);
    }


}
