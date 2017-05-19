package com.sky.tools.base;

import android.app.Application;
import android.content.Context;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/17.
 */

public abstract class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        instance = this;
        super.attachBaseContext(base);
    }

    public static Application getApplication(){
        return instance;
    }
}
