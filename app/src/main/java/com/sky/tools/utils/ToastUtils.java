package com.sky.tools.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-12-9
 */
public class ToastUtils {
    private static Context context;

    private ToastUtils() {
        throw new AssertionError();
    }

    /**
     * 初始化设置context，推荐使用application，设置之后就可以调用不带context参数的方法了，否则会抛出空指针异常
     * */
    public static void init(Context context){
        ToastUtils.context = context;
    }

    public static void showShort(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, CharSequence text){
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showShort(int resId){
        NullUitls.checkContextNull(context);
        showShort(context, resId);
    }

    public static void showShort(CharSequence text){
        NullUitls.checkContextNull(context);
        showShort(context, text);
    }

    public static void showLong(Context context, int resId){
        show(context, context.getResources().getText(resId), Toast.LENGTH_LONG);
    }

    public static void showLong(int resId){
        NullUitls.checkContextNull(context);
        showLong(context, resId);
    }

    public static void showLong(Context context, CharSequence text){
        show(context, text, Toast.LENGTH_LONG);
    }

    public static void showLong(CharSequence text){
        showLong(context, text);
    }

    private static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
