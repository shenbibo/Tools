package com.sky.tools.log.backup;

import android.util.Log;

import com.sky.tools.log.AbsTree;

/**
 * [打印日志到logcat]
 * [detail]
 * Created by Sky on 2017/5/26.
 */

public class LogcatTree extends AbsTree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.ASSERT) {
            Log.wtf(tag, message);
        } else {
            Log.println(priority, tag, message);
        }
    }
}
