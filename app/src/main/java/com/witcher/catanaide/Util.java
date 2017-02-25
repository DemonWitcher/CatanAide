package com.witcher.catanaide;

import android.content.Context;

import java.util.Random;

/**
 * Created by witcher on 2017/2/18 0018.
 */

public class Util {

    public static int getNewNo(){
        Random random = new Random();
        return random.nextInt(6)+random.nextInt(6)+2;
    }
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
