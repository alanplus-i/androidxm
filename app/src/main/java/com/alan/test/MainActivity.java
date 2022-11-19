package com.alan.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alan.xm.lib.utils.FileUtils;
import com.alan.xm.lib.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.d("message1", "message2", "message3");
        Logger.d("message");
        Logger.d(1);
        Logger.d(true);
        Logger.d(11111111111L);
        Logger.d(FileUtils.getSystemCachePath());
        Logger.f("test");
        Logger.f("hello");
        Logger.ext("hello");
        Logger.ext("alan");


    }
}