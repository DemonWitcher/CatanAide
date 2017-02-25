package com.witcher.catanaide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class CustomDrawerLayout extends android.support.v4.widget.DrawerLayout{

    private boolean mIsIntercept = true;

    public boolean ismIsIntercept() {
        return mIsIntercept;
    }

    public void setmIsIntercept(boolean mIsIntercept) {
        this.mIsIntercept = mIsIntercept;
    }

    public CustomDrawerLayout(Context context) {
        super(context);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        if(mIsIntercept){
            return super.onInterceptTouchEvent(ev);
        }else{
            super.onInterceptTouchEvent(ev);
            return false;
        }
    }
}
