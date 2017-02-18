package com.witcher.catanaide;

import java.util.Random;

/**
 * Created by witcher on 2017/2/18 0018.
 */

public class Util {

    public static int getNewNo(){
        Random random = new Random();
        return random.nextInt(6)+random.nextInt(6)+2;
    }
}
