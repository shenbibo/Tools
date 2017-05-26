package com.sky.tools.log;

/**
 * [默认实现中，只处理合成和的字符串]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

import android.support.annotation.Nullable;

import static com.sky.tools.log.Slog.*;

public abstract class AbsTree implements Tree {
    //    @Override
    //    public void v(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        prepareLog(VERBOSE, prefixTag, null, compoundMsg, normalMsg, args);
    //    }

    @Override
    public void v(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(VERBOSE, tag, t, compoundMsg, normalMsg, args);
    }

    //    @Override
    //    public void d(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        prepareLog(DEBUG, prefixTag, null, compoundMsg, normalMsg, args);
    //    }

    @Override
    public void d(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(DEBUG, tag, t, compoundMsg, normalMsg, args);
    }

    @Override
    public void d(String tag, String compoundMsg, @Nullable Object object) {
        prepareLog(DEBUG, tag, null, compoundMsg, (object == null ? null : object.toString()));
    }

    //    @Override
    //    public void i(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        prepareLog(INFO, prefixTag, null, compoundMsg, normalMsg, args);
    //    }

    @Override
    public void i(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(INFO, tag, t, compoundMsg, normalMsg, args);
    }

    //    @Override
    //    public void w(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        prepareLog(WARN, prefixTag, null, compoundMsg, normalMsg, args);
    //    }

    @Override
    public void w(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(WARN, tag, t, compoundMsg, normalMsg, args);
    }

    //    @Override
    //    public void e(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        prepareLog(ERROR, prefixTag, null, compoundMsg, normalMsg, args);
    //    }

    @Override
    public void e(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(ERROR, tag, t, compoundMsg, normalMsg, args);
    }

    //    @Override
    //    public void wtf(String prefixTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
    //        prepareLog(ASSERT, prefixTag, null, compoundMsg, normalMsg, args);
    //    }

    @Override
    public void wtf(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(ASSERT, tag, t, compoundMsg, normalMsg, args);
    }

    //    @Override
    //    public void log(int priority, String prefixTag, Throwable t, String compoundMsg, @Nullable String normalMsg,
    //            @Nullable Object... args) {
    //        prepareLog(priority, prefixTag, t, compoundMsg, normalMsg, args);
    //    }

    /**
     * 默认实现中只处理合成后的日志，不处理原始日志，所有的上述方法都调用该方法
     */
    protected void prepareLog(int priority, String tag, Throwable t, String compoundMsg, @Nullable String normalMsg,
            @Nullable Object... args) {
        if (isLoggable(priority, tag)) {
            log(priority, tag, compoundMsg, t);
        }
    }

    /**
     * 默认返回true
     * */
    protected boolean isLoggable(int priority, String tag) {
        return true;
    }

    protected abstract void log(int priority, String tag, String message, Throwable t);
}
