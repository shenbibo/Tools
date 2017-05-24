package com.sky.tools.utils.log;

import timber.log.Timber;
import timber.log.Timber.DebugTree;
import timber.log.Timber.Tree;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/24.
 */

public class LogAdapterWrapper {
    private String defaultTag = "";
    private final ThreadLocal<String> explicitTag = new ThreadLocal<>();

    /** Log a verbose message with optional format args. */
    public void v(String message, Object... args) {
        Timber.tag(getTag()).v(message, args);
    }

    /** Log a verbose exception and a message with optional format args. */
    public void v(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).v(t, message, args);
    }

    /** Log a verbose exception. */
    public void v(Throwable t) {
        Timber.tag(getTag()).v(t);
    }

    /** Log a debug message with optional format args. */
    public void d(String message, Object... args) {
        Timber.tag(getTag()).d(message, args);
    }

    /** Log a debug exception and a message with optional format args. */
    public void d(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).d(t, message, args);
    }

    /** Log a debug exception. */
    public void d(Throwable t) {
        Timber.tag(getTag()).d(t);
    }

    /** Log an info message with optional format args. */
    public void i(String message, Object... args) {
        Timber.tag(getTag()).i(message, args);
    }

    /** Log an info exception and a message with optional format args. */
    public void i(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).i(t, message, args);
    }

    /** Log an info exception. */
    public void i(Throwable t) {
        Timber.tag(getTag()).i(t);
    }

    /** Log a warning message with optional format args. */
    public void w(String message, Object... args) {
        Timber.tag(getTag()).w(message, args);
    }

    /** Log a warning exception and a message with optional format args. */
    public void w(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).w(t, message, args);
    }

    /** Log a warning exception. */
    public void w(Throwable t) {
        Timber.tag(getTag()).i(t);
    }

    /** Log an error message with optional format args. */
    public void e(String message, Object... args) {
        Timber.tag(getTag()).e(message, args);
    }

    /** Log an error exception and a message with optional format args. */
    public void e(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).e(t, message, args);
    }

    /** Log an error exception. */
    public void e(Throwable t) {
        Timber.tag(getTag()).e(t);
    }

    /** Log an assert message with optional format args. */
    public void wtf(String message, Object... args) {
        Timber.tag(getTag()).wtf(message, args);
    }

    /** Log an assert exception and a message with optional format args. */
    public void wtf(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).wtf(t, message, args);
    }

    /** Log an assert exception. */
    public void wtf(Throwable t) {
        Timber.tag(getTag()).wtf(t);
    }

    /** Log at {@code priority} a message with optional format args. */
    public void log(int priority, String message, Object... args) {
        Timber.tag(getTag()).log(priority, message, args);
    }

    /** Log at {@code priority} an exception and a message with optional format args. */
    public void log(int priority, Throwable t, String message, Object... args) {
        Timber.tag(getTag()).log(priority, t, message, args);
    }

    /** Log at {@code priority} an exception */
    public void log(int priority, Throwable t) {
        Timber.tag(getTag()).log(priority, t);
    }

    /** 添加一个新的日志打印的适配器到日志打印器中 */
    public void plant(Tree tree) {
        Timber.plant(tree);
    }

    /** 移除指定的tree */
    public void removeLogAdapter(Tree tree) {
        Timber.uproot(tree);
    }

    /** 移除所有的tree */
    public void removeAllLogAdapter() {
        Timber.uprootAll();
    }

    public void setDefaultTag(String tag) {
        defaultTag = tag;
    }

    /**
     * 设置Tag，用于给下一次调用log方法使用
     */
    public LogAdapterWrapper setTag(String tag) {
        if (tag != null) {
            explicitTag.set(tag);
        }
        return this;
    }

    private String getTag() {
        String tag = explicitTag.get();
        if (tag != null) {
            explicitTag.remove();
        } else {
            tag = defaultTag;
        }

        return tag;
    }
}
