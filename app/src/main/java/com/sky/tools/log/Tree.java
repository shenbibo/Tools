package com.sky.tools.log;

import android.support.annotation.Nullable;

/**
 * 传递到tree中的数据可能是已经处理过的，所以需要返回原始数据
 * <p>
 * [detail]
 * Created by Sky on 2017/5/25.
 */

public interface Tree {
    //    /** Log a verbose message with optional format args. */
    //    void v(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    /** Log a verbose message with optional format args. */
    void v(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    //    /** Log a debug message with optional format args. */
    //    void d(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    /** Log a debug message with optional format args. */
    void d(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    /** Log a debug message and the object */
    void d(String tag, String compoundMsg, @Nullable Object object);

    //    /** Log an info message with optional format args. */
    //    void i(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    /** Log an info message with optional format args. */
    void i(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    //    /** Log a warning message with optional format args. */
    //    void w(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    /** Log a warning exception and a message with optional format args. */
    void w(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    //    /** Log an error message with optional format args. */
    //    void e(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    /** Log an error exception and a message with optional format args. */
    void e(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    //    /** Log an assert message with optional format args. */
    //    void wtf(String defaultTag, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    /** Log an assert message with optional format args. */
    void wtf(String tag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);

    //    /** Log at {@code priority} an exception and a message with optional format args. */
    //    void log(int priority, String defaultTag, Throwable t, String compoundMsg, @Nullable String normalMsg, @Nullable Object... args);
}
