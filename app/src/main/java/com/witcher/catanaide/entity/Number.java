package com.witcher.catanaide.entity;

/**
 * Created by witcher on 2017/2/18 0018.
 */

public class Number {
    public String name;
    public int num;

    public Number(String name, int num) {
        this.name = name;
        this.num = num;
    }
    public Number(int num,String name) {
        this.name = name;
        this.num = num;
    }
}
