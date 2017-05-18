package com.sky.tools.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * [设备工具类，如获取设备尺寸等]
 * Created by Sky on 2017/5/18.
 */

public class DeviceUtils {
    /**
     * 默认设备尺寸，当设备尺寸计算异常时，返回该值
     * */
    public static final double DEFAULT_DEVICE_SIZE = 5.0;

    /**
     * 获取设置的尺寸，实际使用中某些设备上采用标准方法计算设备尺寸会偏大，此时返回{@link #DEFAULT_DEVICE_SIZE}
     */
    public static double deviceSize(Context context) {
        NullUtils.checkContextNull(context);

        DisplayMetrics displayMetrics = getMetrics(context);
        float ratio = 2 * displayMetrics.densityDpi / (displayMetrics.xdpi + displayMetrics.ydpi);
        // 如果2倍densityDpi 与 (xdpi + ydpi) 之和的比值与1偏差比较大，此时认为系统的参数存在问题
        // 直接返回默认尺寸
        if (ratio > 0.65 && ratio < 1.5) {
            double windowX = Math.pow(displayMetrics.widthPixels / displayMetrics.xdpi, 2);
            double windowY = Math.pow(displayMetrics.heightPixels / displayMetrics.ydpi, 2);
            return Math.sqrt(windowX + windowY);
        }

        return DEFAULT_DEVICE_SIZE;
    }

    private static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
