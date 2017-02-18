package com.witcher.catanaide;

import android.util.Log;

/**
 * Created by witcher on 2017/2/10 0010.
 */

public class L {
    private static String TAG = "witcher";
    private static boolean DEBUG = true;
    public static void i(String s){
        if(DEBUG){
            Log.i(TAG,s);
        }
    }
}
