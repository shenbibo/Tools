package com.sky.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Map;
import java.util.Set;

/**
 * SpUtils, easy to get or put data
 * 在调用里面任何的方法之前，必须要先调用{@link #init(Context, String)}方法
 * <p>
 * 方法中不带xmlName参数的方法，默认都是对{@link #DEFAULT_XML_NAME}xml文件操作
 */
public class SpUtils {

    public static String DEFAULT_XML_NAME = "defaultConfig";
    /**
     * 用于保存string数组的分割符，此处是为了防止被其他的碰撞上了
     */
    private static final String STRING_ARRAY_SPILT = "2,天0;1工@=#7,p具;s:0项5=19";
    private static Context context;

    private SpUtils() {
        throw new AssertionError();
    }

    /**
     * @param context 持久化持有context，使用application的实例，如果设置了该值，则后面可以调用不带Context参数的方法
     */
    public static void init(Context context, String defaultConfigXmlName) {
        SpUtils.context = context;
        DEFAULT_XML_NAME = defaultConfigXmlName;
    }

    /**
     * put string preferences
     *
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @param xmlName 要保存到的xml文件的名称
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(String xmlName, String key, String value) {
        return getEditor(xmlName).putString(key, value).commit();
    }

    /**
     * put string preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(String key, String value) {
        return putString(DEFAULT_XML_NAME, key, value);
    }

    public static String getString(String xmlName, String key, String defaultValue) {
        return getSharedPreferences(xmlName).getString(key, defaultValue);
    }

    /**
     * get string preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public static String getString(String key, String defaultValue) {
        return getString(DEFAULT_XML_NAME, key, defaultValue);
    }

    public static boolean putInt(String xmlName, String key, int value) {
        return getEditor(xmlName).putInt(key, value).commit();
    }

    /**
     * put int preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(String key, int value) {
        return putInt(DEFAULT_XML_NAME, key, value);
    }

    public static int getInt(String xmlName, String key, int defaultValue) {
        return getSharedPreferences(xmlName).getInt(key, defaultValue);
    }

    /**
     * get int preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(String key, int defaultValue) {
        return getInt(DEFAULT_XML_NAME, key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param xmlName
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(String xmlName, String key, long value) {
        return getEditor(xmlName).putLong(key, value).commit();
    }

    public static boolean putLong(String key, long value) {
        return putLong(DEFAULT_XML_NAME, key, value);
    }

    /**
     * get long preferences
     *
     * @param xmlName
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(String xmlName, String key, long defaultValue) {
        return getSharedPreferences(xmlName).getLong(key, defaultValue);
    }

    /**
     * get long preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        return getLong(DEFAULT_XML_NAME, key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param xmlName
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(String xmlName, String key, float value) {
        return getEditor(xmlName).putFloat(key, value).commit();
    }

    /**
     * put float preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(String key, float value) {
        return putFloat(DEFAULT_XML_NAME, key, value);
    }

    /**
     * get float preferences
     *
     * @param xmlName
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     */
    public static float getFloat(String xmlName, String key, float defaultValue) {
        return getSharedPreferences(xmlName).getFloat(key, defaultValue);
    }

    /**
     * get float preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(String key, float defaultValue) {
        return getFloat(DEFAULT_XML_NAME, key, defaultValue);
    }

    public static boolean putDouble(String xmlName, String key, double value) {
        return putString(xmlName, key, String.valueOf(value));
    }

    public static boolean putDouble(String key, double value) {
        return putString(DEFAULT_XML_NAME, key, String.valueOf(value));
    }

    public static double getDouble(String xmlName, String key, double defaultValue) {
        String strValue = getString(xmlName, key, "unknow");
        if (strValue.equals("unknow")) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(strValue);
        } catch (NumberFormatException e) {
            // empty
        }
        return defaultValue;
    }

    public static double getDouble(String key, double defaultValue) {
        return getDouble(DEFAULT_XML_NAME, key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param xmlName
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(String xmlName, String key, boolean value) {
        return getEditor(xmlName).putBoolean(key, value).commit();
    }

    public static boolean putBoolean(String key, boolean value) {
        return putBoolean(DEFAULT_XML_NAME, key, value);
    }

    /**
     * get boolean preferences, default is false
     *
     * @param xmlName
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     */
    public static boolean getBoolean(String xmlName, String key, boolean defaultValue) {
        return getSharedPreferences(xmlName).getBoolean(key, defaultValue);
    }

    /**
     * get boolean preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(DEFAULT_XML_NAME, key, defaultValue);
    }

    public static boolean putStringSet(String xmlName, String key, Set<String> values) {
        return getEditor(xmlName).putStringSet(key, values).commit();
    }

    public static boolean putStringSet(String key, Set<String> values) {
        return getEditor(DEFAULT_XML_NAME).putStringSet(key, values).commit();
    }

    public static Set<String> getStringSet(String xmlName, String key, Set<String> defaultValues) {
        return getSharedPreferences(xmlName).getStringSet(key, defaultValues);
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValues) {
        return getStringSet(DEFAULT_XML_NAME, key, defaultValues);
    }

    public static boolean putStringArray(String xmlName, String key, @NonNull String[] values) {
        // 以defaultValues[0]STRING_ARRAY_SPILT....defaultValues[End]的方式组装起来
        String joinedValues = TextUtil.join(STRING_ARRAY_SPILT, values);
        return getEditor(xmlName).putString(key, joinedValues).commit();
    }

    public static boolean putStringArray(String key, @NonNull String[] values) {
        return putStringArray(DEFAULT_XML_NAME, key, values);
    }

    public static String[] getStringArray(String xmlName, String key, String[] defaultValues) {
        String tempValue = getSharedPreferences(xmlName).getString(key, null);
        if (tempValue == null) {
            return defaultValues;
        }

        return tempValue.split(STRING_ARRAY_SPILT);
    }

    public static String[] getStringArray(String key, String[] defaultValues) {
        return getStringArray(DEFAULT_XML_NAME, key, defaultValues);
    }

    public static Map<String, ?> getAll(String xmlName) {
        return getSharedPreferences(xmlName).getAll();
    }

    public static Map<String, ?> getAll() {
        return getSharedPreferences(DEFAULT_XML_NAME).getAll();
    }

    public static boolean remove(String xmlName, String key) {
        return getEditor(xmlName).remove(key).commit();
    }

    public static boolean remove(String key) {
        return remove(DEFAULT_XML_NAME, key);
    }

    public static boolean contains(String xmlName, String key) {
        return getSharedPreferences(xmlName).contains(key);
    }

    public static boolean contains(String key) {
        return contains(DEFAULT_XML_NAME, key);
    }

    public static boolean clear(String xmlName) {
        return getEditor(xmlName).clear().commit();
    }

    public static boolean clear() {
        return getEditor(DEFAULT_XML_NAME).clear().commit();
    }

    private static Editor getEditor(String xmlName) {
        return getSharedPreferences(xmlName).edit();
    }

    private static SharedPreferences getSharedPreferences(String xmlName) {
        return context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
    }
}
