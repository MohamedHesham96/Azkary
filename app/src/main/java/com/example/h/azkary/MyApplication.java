package com.example.h.azkary;

/**
 * Created by H on 29/10/2016.
 */

public class MyApplication extends android.app.Application {

    static String zekrName;


    MyApplication() {
        zekrName = "";
    }
    public static void setZekrName(String name) {

        zekrName = name;
    }

    public static String getZekrName() {

        return zekrName;
    }

}
