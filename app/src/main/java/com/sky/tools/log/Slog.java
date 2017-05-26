package com.sky.tools.log;

import com.sky.tools.log.backup.LogAdapter;
import com.sky.tools.log.backup.LogAdapterWrapper;

/**
 * 日志工具类
 * 使用本日志类必须先调用初始化方法{@link Slog#init(AbsTree)}
 * <p>
 * 默认日志的Tag值为{@code Android}
 * <br>默认打印全部的日志
 * <br>初始化时必须指定一个执行日志打印的{@link AbsTree}的对象
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
     * Priority constant for the println method；use Log.wtf.
     */
    public static final int ASSERT = 7;

    /**
     * no log
     */
    public static final int NONE = 8;

    /**
     * 默认日志TAG
     */
    public static final String DEFAULT_TAG = "Android";

    /**
     * 默认日志输出级别
     */
    private static int logLevel = FULL;

    private static Printer printer;

    /**
     * Log a verbose message with optional format args.
     */
    public static void v(String message, Object... args) {
        printer.v(message, args);
    }

    //    /**
    //     * Log a verbose exception and a message with optional format args.
    //     */
    //    public static void v(Throwable t, String message, Object... args) {
    //        printer.v(t, message, args);
    //    }

    //    /**
    //     * Log a verbose exception.
    //     */
    //    public static void v(Throwable t) {
    //        printer.v(t);
    //    }

    /**
     * Log a debug message with optional format args.
     */
    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    //    /**
    //     * Log a debug exception and a message with optional format args.
    //     */
    //    public static void d(Throwable t, String message, Object... args) {
    //        printer.d(t, message, args);
    //    }

    //    /**
    //     * Log a debug exception.
    //     */
    //    public static void d(Throwable t) {
    //        printer.d(t);
    //    }

    /**
     * 打印对象如list,map,set array
     */
    public static void d(Object object) {
        printer.d(object);
    }

    /**
     * Log an info message with optional format args.
     */
    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    //    /**
    //     * Log an info exception and a message with optional format args.
    //     */
    //    public static void i(Throwable t, String message, Object... args) {
    //        printer.i(t, message, args);
    //    }

    //    /**
    //     * Log an info exception.
    //     */
    //    public static void i(Throwable t) {
    //        printer.i(t);
    //    }

    /**
     * Log a warning message with optional format args.
     */
    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    public static void w(Throwable t, String message, Object... args) {
        printer.w(t, message, args);
    }

    //    /**
    //     * Log a warning exception.
    //     */
    //    public static void w(Throwable t) {
    //        printer.w(t);
    //    }

    /**
     * Log an error message with optional format args.
     */
    public static void e(String message, Object... args) {
        printer.e(message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void e(Throwable t, String message, Object... args) {
        printer.e(t, message, args);
    }

    //    /**
    //     * Log an error exception.
    //     */
    //    public static void e(Throwable t) {
    //        printer.e(t);
    //    }

    /**
     * Log an assert message with optional format args.
     */
    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    //    /**
    //     * Log an assert exception and a message with optional format args.
    //     */
    //    public static void wtf(Throwable t, String message, Object... args) {
    //        printer.wtf(t, message, args);
    //    }

    //    /**
    //     * Log an assert exception.
    //     */
    //    public static void wtf(Throwable t) {
    //        printer.wtf(t);
    //    }

    //    /**
    //     * Log at {@code priority} a message with optional format args.
    //     * <p>
    //     * the priority legal value must be one of this:{@link #VERBOSE},{@link #DEBUG},{@link #INFO},{@link #WARN},
    //     * {@link #ERROR},{@link #ASSERT}, other value will drop the log
    //     */
    //    public static void log(int priority, String message, Object... args) {
    //        printer.log(priority, message, args);
    //    }

    /**
     * Log at {@code priority} an exception and a message with optional format args.
     */
    public static void log(int priority, String tag, Throwable t, String message, Object... args) {
        printer.log(priority, tag, t, message, args);
    }

    //    /**
    //     * Log at {@code priority} an exception.
    //     */
    //    public static void log(int priority, Throwable t) {
    //        printer.log(priority, t);
    //    }

    public static void json(String json) {
        printer.json(json);
    }

    public static void xml(String xml) {
        printer.xml(xml);
    }

    public static Setting init(AbsTree tree) {
        printer = new LogPrinter();
        return printer.init(tree);
    }

    /**
     * 添加一个新的日志打印的适配器到日志打印器中
     */
    public static void plant(AbsTree tree) {
        printer.plant(tree);
    }

    public static Printer t(String tag) {
        return printer.t(tag);
    }

    public static Printer m(Integer methodCount) {
        return printer.m(methodCount);
    }

    public Printer s(Boolean simpleMode) {
        return printer.s(simpleMode);
    }

    public Printer th(Boolean showThreadInfo) {
        return printer.th(showThreadInfo);
    }


    public Printer t(String tag, Integer methodCount, Boolean simpleMode, Boolean showThreadInfo) {
        return printer.t(tag, methodCount, simpleMode, showThreadInfo);
    }

    /** 获取全局日志配置， 可以通过该对象设置全局配置参数 */
    public static Setting getLogSetting() {
        return printer.getSetting();
    }
}
