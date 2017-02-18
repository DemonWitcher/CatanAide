package com.witcher.catanaide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by witcher on 2017/2/18 0018.
 */

public class CheckBox extends com.gc.materialdesign.views.CheckBox{
    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
