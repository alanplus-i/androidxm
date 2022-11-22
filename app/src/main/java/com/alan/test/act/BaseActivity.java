package com.alan.test.act;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alan.test.R;
import com.alan.test.view.ItemView;
import com.alan.xm.lib.exception.XException;
import com.alan.xm.lib.utils.Logger;
import com.alan.xm.lib.utils.Utils;


/**
 * @author alan ye
 * created on  2022/11/3
 */
public class BaseActivity extends AppCompatActivity {

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    protected View addItemView(String msg) {
        LinearLayout container = findViewById(R.id.content_view);
        ItemView itemView = new ItemView(this);
        itemView.setTitle(msg);
        container.addView(itemView, new LinearLayout.LayoutParams(-1, Utils.dip2px(48)));
        return itemView;
    }


    protected void printThread(String content) {
        Logger.d(content + Thread.currentThread().getName());
        Logger.d("-------------------------------------------");
    }

    protected void printError(XException e) {
        Logger.d("error:" + e.code);
        Logger.d("msg:" + e.message);
        Logger.d("thread:" + Thread.currentThread().getName());
        Logger.d("-------------------------------------------");
    }

    protected void showSuccess() {
        printThread("onSuccess()");
        showMessage("success");
    }

    protected void showFailure(XException e) {
        printError(e);
        showMessage("failure:" + e.code);
    }


    protected void startAct(Class<?> tClass) {
        Intent intent = new Intent();
        intent.setClass(this, tClass);
        startActivity(intent);
    }
}
