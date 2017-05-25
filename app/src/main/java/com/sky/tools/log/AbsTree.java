package com.sky.tools.log;

/**
 * [默认实现中，只处理合成和的字符串]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

import android.support.annotation.Nullable;

import static com.sky.tools.log.Slog.*;

public abstract class AbsTree implements Tree {
    @Override
    public void v(String tag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(VERBOSE, tag, null, compoundMsg, normalMsg, args);
    }

    @Override
    public void d(String tag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(DEBUG, tag, null, compoundMsg, normalMsg, args);
    }

    @Override
    public void d(String tag, String compoundMsg, @Nullable Object object){
        prepareLog(DEBUG, tag, null, compoundMsg, (object == null ? null : object.toString()));
    }

    @Override
    public void i(String tag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(INFO, tag, null, compoundMsg, normalMsg, args);
    }

    @Override
    public void w(String tag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(WARN, tag, null, compoundMsg, normalMsg, args);
    }

    @Override
    public void w(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(VERBOSE, tag, t, compoundMsg, normalMsg, args);
    }

    @Override
    public void e(String tag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(ERROR, tag, null, compoundMsg, normalMsg, args);
    }

    @Override
    public void e(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(ERROR, tag, t, compoundMsg, normalMsg, args);
    }

    @Override
    public void wtf(String tag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args) {
        prepareLog(ASSERT, tag, null, compoundMsg, normalMsg, args);
    }

    @Override
    public void log(int priority, String tag, Throwable t, String compoundMsg, @Nullable String normalMsg,
            @Nullable Object... args) {
        prepareLog(priority, tag, t, compoundMsg, normalMsg, args);
    }

    /**
     * 默认实现中只处理合成后的日志，不处理原始日志
     */
    protected void prepareLog(int priority, String tag, Throwable t, String compoundMsg, @Nullable String primitiveMsg, @Nullable
            Object... args) {
        log(priority, tag, compoundMsg, t);
    }

    protected abstract void log(int priority, String tag, String message, Throwable t);
}
