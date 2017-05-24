package com.sky.tools.utils.log;

import timber.log.Timber;
import timber.log.Timber.DebugTree;
import timber.log.Timber.Tree;

/**
 * 日志工具类
 * <p>
 * 使用本日志类必须先调用初始化方法{@link L#init(Tree, String, int)},
 * {@link L#init(Tree, String)},{@link L#init(Tree)},{@link L#init()}中的一个。
 * <p>
 * <br>默认日志的Tag值为{@code Android}
 * <br>默认打印全部的日志
 * <br>初始化时必须指定一个执行日志打印的{@link Tree}的对象，默认使用{@link timber.log.Timber.DebugTree}
 * <p>
 * Created by Sky on 2017/5/23.
 */

public final class L {
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

    private static String defaultTag = DEFAULT_TAG;

    /** 默认日志输出级别 */
    private static int logLevel = FULL;

    /** Log a verbose message with optional format args. */
    public static void v(String message, Object... args) {
        if (isLoggable(VERBOSE)) {
            Timber.tag(defaultTag).v(message, args);
        }
    }

    /** Log a verbose exception and a message with optional format args. */
    public static void v(Throwable t, String message, Object... args) {
        if (isLoggable(VERBOSE)) {
            Timber.tag(defaultTag).v(t, message, args);
        }
    }

    /** Log a verbose exception. */
    public static void v(Throwable t) {
        if (isLoggable(VERBOSE)) {
            Timber.tag(defaultTag).v(t);
        }
    }

    /** Log a verbose message with optional format args. */
    public static void v(String tag, String message, Object... args) {
        if (isLoggable(VERBOSE)) {
            Timber.tag(tag).v(message, args);
        }
    }

    /** Log a verbose exception and a message with optional format args. */
    public static void v(String tag, Throwable t, String message, Object... args) {
        if (isLoggable(VERBOSE)) {
            Timber.tag(tag).v(t, message, args);
        }
    }

    /** Log a verbose exception. */
    public static void v(String tag, Throwable t) {
        if (isLoggable(VERBOSE)) {
            Timber.tag(tag).v(t);
        }
    }

    /** Log a debug message with optional format args. */
    public static void d(String message, Object... args) {
        if (isLoggable(DEBUG)) {
            Timber.tag(defaultTag).d(message, args);
        }
    }

    /** Log a debug exception and a message with optional format args. */
    public static void d(Throwable t, String message, Object... args) {
        if (isLoggable(DEBUG)) {
            Timber.tag(defaultTag).d(t, message, args);
        }
    }

    /** Log a debug exception. */
    public static void d(Throwable t) {
        if (isLoggable(DEBUG)) {
            Timber.tag(defaultTag).d(t);
        }
    }

    /** Log a debug message with optional format args. */
    public static void d(String tag, String message, Object... args) {
        if (isLoggable(DEBUG)) {
            Timber.tag(tag).d(message, args);
        }
    }

    /** Log a debug exception and a message with optional format args. */
    public static void d(String tag, Throwable t, String message, Object... args) {
        if (isLoggable(DEBUG)) {
            Timber.tag(tag).d(t, message, args);
        }
    }

    /** Log a debug exception. */
    public static void d(String tag, Throwable t) {
        if (isLoggable(DEBUG)) {
            Timber.tag(tag).d(t);
        }
    }

    /** Log an info message with optional format args. */
    public static void i(String message, Object... args) {
        if (isLoggable(INFO)) {
            Timber.tag(defaultTag).i(message, args);
        }
    }

    /** Log an info exception and a message with optional format args. */
    public static void i(Throwable t, String message, Object... args) {
        if (isLoggable(INFO)) {
            Timber.tag(defaultTag).i(t, message, args);
        }
    }

    /** Log an info exception. */
    public static void i(Throwable t) {
        if (isLoggable(INFO)) {
            Timber.tag(defaultTag).i(t);
        }
    }

    /** Log an info message with optional format args. */
    public static void i(String tag, String message, Object... args) {
        if (isLoggable(INFO)) {
            Timber.tag(tag).i(message, args);
        }
    }

    /** Log an info exception and a message with optional format args. */
    public static void i(String tag, Throwable t, String message, Object... args) {
        if (isLoggable(INFO)) {
            Timber.tag(tag).i(t, message, args);
        }
    }

    /** Log an info exception. */
    public static void i(String tag, Throwable t) {
        if (isLoggable(INFO)) {
            Timber.tag(tag).i(t);
        }
    }

    /** Log a warning message with optional format args. */
    public static void w(String message, Object... args) {
        if (isLoggable(WARN)) {
            Timber.tag(defaultTag).w(message, args);
        }
    }

    /** Log a warning exception and a message with optional format args. */
    public static void w(Throwable t, String message, Object... args) {
        if (isLoggable(WARN)) {
            Timber.tag(defaultTag).w(t, message, args);
        }
    }

    /** Log a warning exception. */
    public static void w(Throwable t) {
        if (isLoggable(WARN)) {
            Timber.tag(defaultTag).i(t);
        }
    }

    /** Log a warning message with optional format args. */
    public static void w(String tag, String message, Object... args) {
        if (isLoggable(WARN)) {
            Timber.tag(tag).w(message, args);
        }
    }

    /** Log a warning exception and a message with optional format args. */
    public static void w(String tag, Throwable t, String message, Object... args) {
        if (isLoggable(WARN)) {
            Timber.tag(tag).w(t, message, args);
        }
    }

    /** Log a warning exception. */
    public static void w(String tag, Throwable t) {
        if (isLoggable(WARN)) {
            Timber.tag(tag).i(t);
        }
    }

    /** Log an error message with optional format args. */
    public static void e(String message, Object... args) {
        if (isLoggable(ERROR)) {
            Timber.tag(defaultTag).e(message, args);
        }
    }

    /** Log an error exception and a message with optional format args. */
    public static void e(Throwable t, String message, Object... args) {
        if (isLoggable(ERROR)) {
            Timber.tag(defaultTag).e(t, message, args);
        }
    }

    /** Log an error exception. */
    public static void e(Throwable t) {
        if (isLoggable(ERROR)) {
            Timber.tag(defaultTag).e(t);
        }
    }

    /** Log an error message with optional format args. */
    public static void e(String tag, String message, Object... args) {
        if (isLoggable(ERROR)) {
            Timber.tag(tag).e(message, args);
        }
    }

    /** Log an error exception and a message with optional format args. */
    public static void e(String tag, Throwable t, String message, Object... args) {
        if (isLoggable(ERROR)) {
            Timber.tag(tag).e(t, message, args);
        }
    }

    /** Log an error exception. */
    public static void e(String tag, Throwable t) {
        if (isLoggable(ERROR)) {
            Timber.tag(tag).e(t);
        }
    }

    /** Log an assert message with optional format args. */
    public static void wtf(String message, Object... args) {
        if (isLoggable(ASSERT)) {
            Timber.tag(defaultTag).wtf(message, args);
        }
    }

    /** Log an assert exception and a message with optional format args. */
    public static void wtf(Throwable t, String message, Object... args) {
        if (isLoggable(ASSERT)) {
            Timber.tag(defaultTag).wtf(t, message, args);
        }
    }

    /** Log an assert exception. */
    public static void wtf(Throwable t) {
        if (isLoggable(ASSERT)) {
            Timber.tag(defaultTag).wtf(t);
        }
    }

    /** Log an assert message with optional format args. */
    public static void wtf(String tag, String message, Object... args) {
        if (isLoggable(ASSERT)) {
            Timber.tag(tag).e(message, args);
        }
    }

    /** Log an assert exception and a message with optional format args. */
    public static void wtf(String tag, Throwable t, String message, Object... args) {
        if (isLoggable(ASSERT)) {
            Timber.tag(tag).e(t, message, args);
        }
    }

    /** Log an assert exception. */
    public static void wtf(String tag, Throwable t) {
        if (isLoggable(ASSERT)) {
            Timber.tag(tag).e(t);
        }
    }

    /** Log at {@code priority} a message with optional format args. */
    public static void log(int priority, String message, Object... args) {
        if (isLoggable(priority)) {
            Timber.tag(defaultTag).log(priority, message, args);
        }
    }

    /** Log at {@code priority} an exception and a message with optional format args. */
    public static void log(int priority, Throwable t, String message, Object... args) {
        if (isLoggable(priority)) {
            Timber.tag(defaultTag).log(priority, t, message, args);
        }
    }

    /** Log at {@code priority} an exception. */
    public static void log(int priority, Throwable t) {
        if (isLoggable(priority)) {
            Timber.tag(defaultTag).log(priority, t);
        }
    }

    /** Log at {@code priority} a message with optional format args. */
    public static void log(int priority, String tag, String message, Object... args) {
        if (isLoggable(priority)) {
            Timber.tag(tag).log(priority, message, args);
        }
    }

    /** Log at {@code priority} an exception and a message with optional format args. */
    public static void log(int priority, String tag, Throwable t, String message, Object... args) {
        if (isLoggable(priority)) {
            Timber.tag(tag).log(priority, t, message, args);
        }
    }

    /** Log at {@code priority} an exception. */
    public static void log(int priority, String tag, Throwable t) {
        if (isLoggable(priority)) {
            Timber.tag(tag).log(priority, t);
        }
    }

    public static void init(){
        init(new DebugTree());
    }

    public static void init(Tree tree){
        init(tree, DEFAULT_TAG);
    }

    public static void init(Tree tree, String defaultTag){
        init(tree, defaultTag, FULL);
    }

    public static void init(Tree tree, String defaultTag, int logLevel){
        plant(tree);
        setDefaultTag(defaultTag);
        setLogLevel(logLevel);
    }

    /** 添加一个新的日志打印的适配器到日志打印器中 */
    public static void plant(Tree tree) {
        Timber.plant(tree);
    }

    /** 移除指定的tree */
    public static void removeTree(Tree tree) {
        Timber.uproot(tree);
    }

    /** 移除所有的tree */
    public static void removeAllTrees() {
        Timber.uprootAll();
    }

    public static void setDefaultTag(String tag) {
        defaultTag = tag;
    }

    public static void setLogLevel(int logLevel) {
        L.logLevel = logLevel;
    }

    private static boolean isLoggable(int logLevel) {
        return logLevel >= L.logLevel;
    }
}
