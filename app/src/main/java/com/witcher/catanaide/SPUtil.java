package com.witcher.catanaide;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by witcher on 2017/2/18 0018.
 */
public class SPUtil {

    public static int getPeopleNum(Context context) {
        return context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE).getInt("peopleNum", 4);
    }

    public static void setPeopleNum(Context context,int num){
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), context.MODE_PRIVATE).edit();
        editor.putInt("peopleNum",num);
        editor.commit();
    }
}

