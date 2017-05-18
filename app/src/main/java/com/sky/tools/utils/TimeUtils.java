package com.sky.tools.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimeUtils
 * <br>默认时间显示yyyy-MM-dd HH:mm:ss, 日期显示格式yyyy-MM-dd
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #getDefaultDateFormat()}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, getDefaultDateFormat());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #getDefaultDateFormat()}
     * 
     * @return
     */
    public static String getCurrentTime() {
        return getTime(getCurrentTimeMillis());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTime(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeMillis(), dateFormat);
    }

    /**
     * 获取日期格式化对象
     * */
    public static SimpleDateFormat getSimpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 获取默认日期格式化yyyy-MM-dd HH:mm:ss
     * */
    public static SimpleDateFormat getDefaultDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
