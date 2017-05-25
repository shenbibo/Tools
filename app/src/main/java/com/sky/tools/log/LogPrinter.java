package com.sky.tools.log;

import android.app.backup.FullBackupDataOutput;
import android.support.annotation.Nullable;

import static com.sky.tools.log.Helper.*;
import static com.sky.tools.log.Slog.*;

/**
 * [日志中间处理转发类]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

public class LogPrinter implements Printer {
    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 3;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private Setting setting = new Setting();
    private ThreadLocal<String> localTag = new ThreadLocal<>();
    private ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();
    private ThreadLocal<Boolean> localSimpleMode = new ThreadLocal<>();
    private ThreadLocal<Boolean> localHideThreadInfo = new ThreadLocal<>();

    public LogPrinter(AbsTree logTree) {
        plant(logTree);
    }

    @Override
    public void v(String msg, @Nullable Object... args) {

    }

    @Override
    public void d(String msg, @Nullable Object... args) {

    }

    @Override
    public void d(Object object) {

    }

    @Override
    public void i(String msg, @Nullable Object... args) {

    }

    @Override
    public void w(String msg, @Nullable Object... args) {

    }

    @Override
    public void w(Throwable t, String msg, @Nullable Object... args) {

    }

    @Override
    public void e(String msg, @Nullable Object... args) {

    }

    @Override
    public void e(Throwable t, String msg, @Nullable Object... args) {

    }

    @Override
    public void wtf(String msg, @Nullable Object... args) {

    }

    @Override
    public synchronized void log(int priority, String tag, Throwable t, String msg, @Nullable Object... args) {

    }

    @Override
    public void json(String json) {
        d(covertJson(json));
    }

    @Override
    public void xml(String xml) {
        d(covertXml(xml));
    }

    @Override
    public Printer t(String tag) {
        if (tag != null) {
            localTag.set(tag);
        }
        return this;
    }

    @Override
    public Printer m(Integer methodCount) {
        if (methodCount != null) {
            localMethodCount.set(methodCount);
        }
        return this;
    }

    @Override
    public Printer s(Boolean simpleMode) {
        if (simpleMode != null) {
            localSimpleMode.set(simpleMode);
        }
        return this;
    }

    @Override
    public Printer th(Boolean hideThreadInfo) {
        if (hideThreadInfo != null) {
            localHideThreadInfo.set(hideThreadInfo);
        }
        return this;
    }

    @Override
    public Printer t(String tag, Integer methodCount, Boolean simpleMode, Boolean hideThreadInfo) {
        t(tag);
        m(methodCount);
        s(simpleMode);
        th(hideThreadInfo);
        return this;
    }

    public void plant(AbsTree tree) {
        Timber.plant(tree);
    }

    public Setting getSetting() {
        return setting;
    }

    private synchronized void log(int priority, Throwable t, Object originalObject, String originalMsg, Object... args) {
        if (!isLoggable(priority) || !isLegalPriority(priority)) {
            return;
        }

        if (originalObject == null && originalMsg == null) {
            return;
        }

        // 简单模式不组装消息直接返回
        if (isSimpleMode()) {
            onSimpleModeLog(priority, t, originalObject, originalMsg, args);
            return;
        }
    }

    private void onSimpleModeLog(int priority, Throwable t, Object originalObject, String originalString, Object... args) {
        String compoundMsg;
        if (originalObject == null) {
            compoundMsg = formatMessage(originalString, args);
        } else {
            compoundMsg = parseObject(originalObject);
        }

        String[] spiltString = splitString(compoundMsg);
        for (int i = 0; i < spiltString.length; i++) {
            // 注意当一个msg被拆分时，只有在最后一次输出完全时才会传递原始参数，前面都会传null
            if(i != spiltString.length -1 ){
                dispatchLog(priority, t, null, spiltString[i], null);
            }else {
                dispatchLog(priority, t, originalObject, spiltString[i], originalString, args);
            }
        }
    }

    private void dispatchLog(int priority, Throwable t, Object originalObject, String compoundMsg, String originalString,
            Object... args) {
        String tag = getTag();
        if (originalObject == null) {
            Timber.log(priority, tag, t, compoundMsg, originalString, args);
        } else {
            Timber.log(priority, tag, compoundMsg, originalObject);
        }
    }

    private String getTag() {
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
        } else {
            tag = setting.getDefaultTag();
        }
        return tag;
    }

    private int getMethodCount() {
        Integer count = localMethodCount.get();
        if (count != null) {
            localMethodCount.remove();
        } else {
            count = setting.getMethodCount();
        }
        return count;
    }

    private boolean isSimpleMode() {
        Boolean simpleMode = localSimpleMode.get();
        if (simpleMode != null) {
            localSimpleMode.remove();
        } else {
            simpleMode = setting.isSimpleMode();
        }
        return simpleMode;
    }

    private boolean isHideThreadInfo() {
        Boolean hideThreadInfo = localHideThreadInfo.get();
        if (hideThreadInfo != null) {
            localHideThreadInfo.remove();
        } else {
            hideThreadInfo = setting.isHideThreadInfo();
        }
        return hideThreadInfo;
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LogPrinter.class.getName()) && !name.equals(Slog.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private boolean isLoggable(int priority) {
        return priority >= setting.getLogLevel();
    }

    private boolean isLegalPriority(int priority) {
        return priority > FULL && priority < NONE;
    }
}
