package com.alan.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alan.test.R;

/**
 * @author alan ye
 * created on  2022/11/3
 */
public class ItemView extends RelativeLayout {

    private TextView textView;

    public ItemView(Context context) {
        super(context);
        init(null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_item, this);
        textView = findViewById(R.id.item_title);
        if (null != attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ItemViewStyle);
            String string = typedArray.getString(R.styleable.ItemViewStyle_item_title);
            textView.setText(string);
            typedArray.recycle();
        }
    }

    public void setTitle(String title){
        textView.setText(title);
    }
}
