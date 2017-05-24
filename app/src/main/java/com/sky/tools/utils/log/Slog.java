package com.sky.tools.utils.log;

import timber.log.Timber.Tree;

/**
 * 日志工具类
 * <p>
 * 使用本日志类必须先调用初始化方法{@link Slog#init(LogAdapter, String, int)},
 * {@link Slog#init(LogAdapter, String)},{@link Slog#init(LogAdapter)},{@link Slog#init()}中的一个。
 * <p>
 * <br>默认日志的Tag值为{@code Android}
 * <br>默认打印全部的日志
 * <br>初始化时必须指定一个执行日志打印的{@link Tree}的对象，默认使用{@link timber.log.Timber.DebugTree}
 * <p>
 * Created by Sky on 2017/5/23.
 */

public final class Slog {
    /**
     * All log
     */
    public static final int FULL = 1;

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    /**
     * no log
     */
    public static final int NONE = 8;

    /** 默认日志TAG */
    public static final String DEFAULT_TAG = "Android";

    /** 默认日志输出级别 */
    private static int logLevel = FULL;

    private final static LogAdapterWrapper WRAPPER = new LogAdapterWrapper();

    /** Log a verbose message with optional format args. */
    public static void v(String message, Object... args) {
        WRAPPER.v(message, args);
    }

    /** Log a verbose exception and a message with optional format args. */
    public static void v(Throwable t, String message, Object... args) {
        WRAPPER.v(t, message, args);
    }

    /** Log a verbose exception. */
    public static void v(Throwable t) {
        WRAPPER.v(t);
    }

    /** Log a debug message with optional format args. */
    public static void d(String message, Object... args) {
        WRAPPER.d(message, args);
    }

    /** Log a debug exception and a message with optional format args. */
    public static void d(Throwable t, String message, Object... args) {
        WRAPPER.d(t, message, args);
    }

    /** Log a debug exception. */
    public static void d(Throwable t) {
        WRAPPER.d(t);
    }

    /** Log an info message with optional format args. */
    public static void i(String message, Object... args) {
        WRAPPER.i(message, args);
    }

    /** Log an info exception and a message with optional format args. */
    public static void i(Throwable t, String message, Object... args) {
        WRAPPER.i(t, message, args);
    }

    /** Log an info exception. */
    public static void i(Throwable t) {
        WRAPPER.i(t);
    }

    /** Log a warning message with optional format args. */
    public static void w(String message, Object... args) {
        WRAPPER.w(message, args);
    }

    /** Log a warning exception and a message with optional format args. */
    public static void w(Throwable t, String message, Object... args) {
        WRAPPER.w(t, message, args);
    }

    /** Log a warning exception. */
    public static void w(Throwable t) {
        WRAPPER.w(t);
    }

    /** Log an error message with optional format args. */
    public static void e(String message, Object... args) {
        WRAPPER.e(message, args);
    }

    /** Log an error exception and a message with optional format args. */
    public static void e(Throwable t, String message, Object... args) {
        WRAPPER.e(t, message, args);
    }

    /** Log an error exception. */
    public static void e(Throwable t) {
        WRAPPER.e(t);
    }

    /** Log an assert message with optional format args. */
    public static void wtf(String message, Object... args) {
        WRAPPER.wtf(message, args);
    }

    /** Log an assert exception and a message with optional format args. */
    public static void wtf(Throwable t, String message, Object... args) {
        WRAPPER.wtf(t, message, args);
    }

    /** Log an assert exception. */
    public static void wtf(Throwable t) {
        WRAPPER.wtf(t);
    }

    /** Log at {@code priority} a message with optional format args. */
    public static void log(int priority, String message, Object... args) {
        WRAPPER.log(priority, message, args);
    }

    /** Log at {@code priority} an exception and a message with optional format args. */
    public static void log(int priority, Throwable t, String message, Object... args) {
        WRAPPER.log(priority, t, message, args);
    }

    /** Log at {@code priority} an exception. */
    public static void log(int priority, Throwable t) {
        WRAPPER.log(priority, t);
    }

    public static void init() {
        init(new LogAdapter.DebugLogAdapter());
    }

    public static void init(LogAdapter logAdapter) {
        init(logAdapter, DEFAULT_TAG);
    }

    public static void init(LogAdapter logAdapter, String defaultTag) {
        init(logAdapter, defaultTag, FULL);
    }

    public static void init(LogAdapter logAdapter, String defaultTag, int logLevel) {
        plant(logAdapter);
        setDefaultTag(defaultTag);
        setLogLevel(logLevel);
    }

    /** 添加一个新的日志打印的适配器到日志打印器中 */
    public static void plant(LogAdapter logAdapter) {
        WRAPPER.plant(logAdapter);
    }

    public static LogAdapterWrapper t(String tag){
        return WRAPPER.setTag(tag);
    }

    /** 移除指定的tree */
    public static void removeLogAdapter(LogAdapter logAdapter) {
        WRAPPER.removeLogAdapter(logAdapter);
    }

    /** 移除所有的tree */
    public static void removeAllLogAdapter() {
        WRAPPER.removeAllLogAdapter();
    }

    public static void setDefaultTag(String tag) {
        WRAPPER.setDefaultTag(tag);
    }

    public static void setLogLevel(int logLevel) {
        Slog.logLevel = logLevel;
    }

    public static int getLogLevel() {
        return logLevel;
    }

//    private static boolean isPriorityLegal

    private static int getPriority(int priority){
        return priority <= FULL ? VERBOSE : priority;
    }
}
