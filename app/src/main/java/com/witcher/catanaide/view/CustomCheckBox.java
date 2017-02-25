package com.witcher.catanaide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gc.materialdesign.views.CheckBox;

/**
 * Created by witcher on 2017/2/18 0018.
 */

public class CustomCheckBox extends CheckBox {
    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
