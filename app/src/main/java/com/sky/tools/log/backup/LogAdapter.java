package com.sky.tools.log.backup;

import android.util.Log;

import com.sky.tools.log.backup.Slog;

import timber.log.Timber.Tree;

/**
 * [日志的适配器类]
 * [detail]
 * Created by Sky on 2017/5/24.
 */

public abstract class LogAdapter extends Tree {

    /**
     * [默认实现的debugAdapter]
     * [detail]
     * Created by Sky on 2017/5/24.
     */
    public static class DebugLogAdapter extends LogAdapter {
        private static final int MAX_LOG_LENGTH = 4000;

        /**
         * copy from{@link timber.log.Timber.DebugTree}
         */
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
            }
        }
    }

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return priority >= Slog.getLogLevel();
    }
}
