package com.sky.tools.utils.log;

import timber.log.Timber.DebugTree;
import timber.log.Timber.Tree;

/**
 * [日志的适配器类]
 * [detail]
 * Created by Sky on 2017/5/24.
 */

public abstract class LogAdapter extends Tree{

    /**
     * [默认实现的debugAdapter]
     * [detail]
     * Created by Sky on 2017/5/24.
     */

    public static class DebugLogAdapter extends LogAdapter {
        private DebugTree tree = new DebugTree();

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            tree.log(priority, tag, message, t);
        }

        @Override
        protected boolean isLoggable(String tag, int priority) {
            return priority >= Slog.getLogLevel();
        }
    }
}
