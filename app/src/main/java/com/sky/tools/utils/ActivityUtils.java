package com.sky.tools.utils;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [一句话描述类的作用]
 * [详述类的功能。]
 * Created by sky on 2017/5/22.
 */

public class ActivityUtils {
    private static ConcurrentHashMap<String, WeakReference<Activity>> activitys = new ConcurrentHashMap<>();

    public static void addActivity(Activity activity) {
        if (activity != null) {
            activitys.put(activity.getClass().getName(), new WeakReference<>(activity));
        }
    }

    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activitys.remove(activity.getClass().getName());
        }
    }

    public static void removeAndFinishAll() {
        Iterator<Entry<String, WeakReference<Activity>>> iterator = activitys.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, WeakReference<Activity>> entry = iterator.next();
            Activity activity = entry.getValue().get();
            if (isActivityActive(activity)) {
                activity.finish();
            }
        }
    }

    public static boolean isActivityActive(Activity activity) {
        return activity != null && !activity.isFinishing() && (VERSION.SDK_INT < VERSION_CODES.JELLY_BEAN_MR1 ||
                !activity.isDestroyed());
    }
}
