package com.sky.tools.log;

import android.support.annotation.Nullable;

/**
 * [日志打印的中间层，包含数据的转换逻辑接口]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

public interface Printer {
    /** Log a verbose message with optional format args. */
    void v(String normalMsg, @Nullable Object... args);

    /** Log a debug message with optional format args. */
    void d(String normalMsg, @Nullable Object... args);

    /** Log a debug message and the object */
    void d(Object object);

    /** Log an info message with optional format args. */
    void i(String normalMsg, @Nullable Object... args);

    /** Log a warning message with optional format args. */
    void w(String normalMsg, @Nullable Object... args);

    /** Log a warning exception and a message with optional format args. */
    void w(Throwable t, String normalMsg, @Nullable Object... args);

    /** Log an error message with optional format args. */
    void e(String normalMsg, @Nullable Object... args);

    /** Log an error exception and a message with optional format args. */
    void e(Throwable t, String normalMsg, @Nullable Object... args);

    /** Log an assert message with optional format args. */
    void wtf(String normalMsg, @Nullable Object... args);

    /** Log at {@code priority} an exception and a message with optional format args. */
    void log(int priority, String tag, Throwable t, String normalMsg, @Nullable Object... args);
    
    void json(String json);
    
    void xml(String xml);

    /** 设置接下来该线程打印一次日志的tag*/
    Printer t(String tag);

    /** 调用该方法后，确定接下来打印的日志显示堆栈内方法的个数，若{@code simpleCode}为true，则设置无效
     * @param methodCount*/
    Printer m(Integer methodCount);

    /** 为true，则为普通log打印，有最高的效率
     * @param simpleMode*/
    Printer s(Boolean simpleMode);

    /** 调用该方法后，确定接下来打印的日志是否携带线程信息，若{@code simpleCode}为true，则设置无效
     * @param hideThreadInfo*/
    Printer th(Boolean hideThreadInfo);

    Printer t(String tag, Integer methodCount, Boolean simpleMode, Boolean hideThreadInfo);

    Setting getSetting();

    Setting init(Tree tree);

    void plant(Tree tree);
}
