package com.sky.tools.utils;

import android.content.Context;

/**
 * 空对象检查
 * [detail]
 * Created by Sky on 2017/5/18.
 */

public class NullUitls {
    public static void checkNull(Object object){
        if(object == null){
            throw new NullPointerException("object is null");
        }
    }

    public static void checkContextNull(Context context){
        if(context == null){
            throw new NullPointerException("context is null, maybe you could call init(Context) first");
        }
    }
}
