package com.sky.tools.log;

import static android.R.attr.priority;

/**
 * 日志工具类
 * 使用本日志类必须先调用初始化方法{@link Slog#init(Tree)}
 * <p>
 * 默认日志的Tag值为{@code Android}
 * <br>默认打印全部的日志{@link Slog#FULL}
 * <br>打印日志时，可以使用的日志级别只有{@link Slog#VERBOSE},{@link Slog#INFO},{@link Slog#INFO},{@link Slog#WARN},
 * {@link Slog#ERROR},{@link Slog#ASSERT}，其他的值不会打印。
 * <br>初始化时必须指定一个执行日志打印的{@link Tree}的对象
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

    private static LogController logController;
    private static Setting setting;
    private static TreeManager treeManager;

    /**
     * Log a verbose message with optional format args.
     */
    public static void v(String message, Object... args) {
        logController.v(message, args);
    }

    /**
     * Log a debug message with optional format args.
     */
    public static void d(String message, Object... args) {
        logController.d(message, args);
    }

    /**
     * 打印对象如list,map,set array
     */
    public static void d(Object object) {
        logController.d(object);
    }

    /**
     * Log an info message with optional format args.
     */
    public static void i(String message, Object... args) {
        logController.i(message, args);
    }

    /**
     * 打印对象如list,map,set array等
     */
    public static void i(Object object) {
        logController.i(object);
    }

    /**
     * Log a warning message with optional format args.
     */
    public static void w(String message, Object... args) {
        logController.w(message, args);
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    public static void w(Throwable t, String message, Object... args) {
        logController.w(t, message, args);
    }

    /**
     * Log an error exception
     */
    public static void w(Throwable t) {
        w(t, "");
    }

    /**
     * Log an error message with optional format args.
     */
    public static void e(String message, Object... args) {
        logController.e(message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void e(Throwable t, String message, Object... args) {
        logController.e(t, message, args);
    }

    /**
     * Log an error exception
     */
    public static void e(Throwable t) {
        e(t, "");
    }

    /**
     * Log an assert message with optional format args.
     */
    public static void wtf(String message, Object... args) {
        logController.wtf(message, args);
    }

    /**
     * Log at {@code priority} an exception and a message with optional format args.
     * <br>
     * 只有{@link Slog#VERBOSE},{@link Slog#INFO},{@link Slog#INFO},{@link Slog#WARN},{@link Slog#ERROR},
     * {@link Slog#ASSERT}
     * 才打印，其他的值无效
     */
    public static void log(int priority, String tag, Throwable t, String message, Object... args) {
        logController.log(priority, tag, t, message, args);
    }

    public static void json(String json) {
        logController.json(json);
    }

    public static void xml(String xml) {
        logController.xml(xml);
    }

    public static Setting init(Tree tree) {
        if (setting == null) {
            setting = new Setting();
            logController = LogFactory.createLogController();
            LogManager logManager = LogFactory.createLogManager();
            treeManager = logManager;
            logController.init(Slog.class, setting, logManager);
        }
        return setting;
    }

    public static LogController t(String tag) {
        return logController.t(tag);
    }

    public static LogController m(Integer methodCount) {
        return logController.m(methodCount);
    }

    public static LogController s(Boolean simpleMode) {
        return logController.s(simpleMode);
    }

    public static LogController th(Boolean showThreadInfo) {
        return logController.th(showThreadInfo);
    }

    /**
     * 添加一个新的日志打印的适配器到日志打印器中
     */
    public static void plantTree(Tree tree) {
        treeManager.plantTree(tree);
    }

    public static void removeTree(Tree tree) {
        treeManager.removeTree(tree);
    }

    public static void clearTrees() {
        treeManager.clearTrees();
    }

    /**
     * 获取全局日志配置， 可以通过该对象设置全局配置参数
     */
    public static Setting getSetting() {
        return setting;
    }
}
