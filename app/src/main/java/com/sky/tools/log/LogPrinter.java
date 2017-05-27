package com.sky.tools.log;

import android.support.annotation.Nullable;

import static com.sky.tools.log.Helper.covertJson;
import static com.sky.tools.log.Helper.covertXml;
import static com.sky.tools.log.Helper.formatMessage;
import static com.sky.tools.log.Helper.getSimpleClassName;
import static com.sky.tools.log.Helper.getStackTraceString;
import static com.sky.tools.log.Helper.parseObject;
import static com.sky.tools.log.Helper.splitString;
import static com.sky.tools.log.Slog.ASSERT;
import static com.sky.tools.log.Slog.DEBUG;
import static com.sky.tools.log.Slog.ERROR;
import static com.sky.tools.log.Slog.FULL;
import static com.sky.tools.log.Slog.INFO;
import static com.sky.tools.log.Slog.NONE;
import static com.sky.tools.log.Slog.VERBOSE;
import static com.sky.tools.log.Slog.WARN;

/**
 * [日志中间处理转发类]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

class LogPrinter implements Printer {
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
    private ThreadLocal<Boolean> localShowThreadInfo = new ThreadLocal<>();

    @Override
    public Setting init(Tree tree) {
        plant(tree);
        return setting;
    }

    @Override
    public void v(String msg, @Nullable Object... args) {
        log(VERBOSE, null, null, null, msg, args);
    }

    @Override
    public void d(String msg, @Nullable Object... args) {
        log(DEBUG, null, null, null, msg, args);
    }

    @Override
    public void d(Object object) {
        log(DEBUG, null, null, object, null);
    }

    @Override
    public void i(String msg, @Nullable Object... args) {
        log(INFO, null, null, null, msg, args);
    }

    @Override
    public void i(Object object) {
        log(INFO, null, null, object, null);
    }

    @Override
    public void w(String msg, @Nullable Object... args) {
        log(WARN, null, null, null, msg, args);
    }

    @Override
    public void w(Throwable t, String msg, @Nullable Object... args) {
        log(WARN, null, t, null, msg, args);
    }

    @Override
    public void e(String msg, @Nullable Object... args) {
        log(ERROR, null, null, null, msg, args);
    }

    @Override
    public void e(Throwable t, String msg, @Nullable Object... args) {
        log(ERROR, null, t, null, msg, args);
    }

    @Override
    public void wtf(String msg, @Nullable Object... args) {
        log(ASSERT, null, null, null, msg, args);
    }

    @Override
    public void log(int priority, String tag, Throwable t, String msg, @Nullable Object... args) {
        log(priority, tag, t, null, msg, args);
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
    public Printer th(Boolean showThreadInfo) {
        if (showThreadInfo != null) {
            localShowThreadInfo.set(showThreadInfo);
        }
        return this;
    }

    @Override
    public Printer t(String tag, Integer methodCount, Boolean simpleMode, Boolean showThreadInfo) {
        t(tag);
        m(methodCount);
        s(simpleMode);
        th(showThreadInfo);
        return this;
    }

    @Override
    public void plant(Tree tree) {
        Timber.plant(tree);
    }

    @Override
    public void removeTree(Tree tree) {
        Timber.removeTree(tree);
    }

    @Override
    public void clearTrees() {
        Timber.clearTrees();
    }

    @Override
    public Setting getSetting() {
        return setting;
    }

    private void log(int priority, String tag, Throwable t, Object originalObject, String originalMsg, Object... args) {
        if (!isLoggable(priority) || !isLegalPriority(priority)) {
            return;
        }

        if (originalObject == null && originalMsg == null) {
            return;
        }

        String finalTag = tag != null ? createCompoundTag(tag) : getTag();
        // 简单模式不组装消息直接返回
        if (isSimpleMode()) {
            logSimpleMode(priority, finalTag, t, originalObject, originalMsg, args);
            return;
        }

        logStandardMode(priority, finalTag, t, originalObject, originalMsg, args);
    }

    private void logSimpleMode(int priority, String tag, Throwable t, Object originalObject, String originalString,
            Object... args) {
        String[] spiltMsg = compoundMsgAndSplit(t, originalObject, originalString, args);
        for (int i = 0; i < spiltMsg.length; i++) {
            // 注意当一个msg被拆分时，只有在最后一次输出完全时才会传递原始参数，前面都会传null
            if (i != spiltMsg.length - 1) {
                dispatchLog(priority, tag, t, null, spiltMsg[i], null);
            } else {
                dispatchLog(priority, tag, t, originalObject, spiltMsg[i], originalString, args);
            }
        }
    }

    private String[] compoundMsgAndSplit(Throwable t, Object originalObject, String originalString, Object... args) {
        String compoundMsg;
        if (originalObject == null) {
            compoundMsg = formatMessage(originalString, args);
        } else {
            compoundMsg = parseObject(originalObject);
        }

        if (t != null) {
            compoundMsg += " : " + getStackTraceString(t);
        }

        return splitString(compoundMsg);
    }

    private synchronized void logStandardMode(int priority, String tag, Throwable t, Object originalObject, String
            originalString, Object... args) {
        int methodCount = getMethodCount();
        logTopBorder(priority, tag);
        logHeaderContent(priority, tag, methodCount);

        if (methodCount > 0) {
            logDivider(priority, tag);
        }

        logContent(priority, tag, t, originalObject, originalString, args);
        logBottomBorder(priority, tag);
    }

    private void logTopBorder(int priority, String tag) {
        dispatchLog(priority, tag, null, null, TOP_BORDER, null);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void logHeaderContent(int priority, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (isShowThreadInfo()) {
            String curThreadName = Thread.currentThread().getName();
            dispatchLog(priority, tag, null, null, getLineCompoundStr(curThreadName), null);
            logDivider(priority, tag);
        }
        String level = "";

        int stackOffset = getStackOffset(trace);

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("║ ")
                   .append(level)
                   .append(getSimpleClassName(trace[stackIndex].getClassName()))
                   .append(".")
                   .append(trace[stackIndex].getMethodName())
                   .append("(")
                   .append(trace[stackIndex].getFileName())
                   .append(":")
                   .append(trace[stackIndex].getLineNumber())
                   .append(")");
            level += "   ";
            dispatchLog(priority, tag, null, null, builder.toString(), null);
        }
    }

    private void logBottomBorder(int priority, String tag) {
        dispatchLog(priority, tag, null, null, BOTTOM_BORDER, null);
    }

    private void logDivider(int priority, String tag) {
        dispatchLog(priority, tag, null, null, MIDDLE_BORDER, null);
    }

    private void logContent(int priority, String tag, Throwable t, Object originalObject, String originalString,
            Object... args) {
        String[] splitMsg = compoundMsgAndSplit(t, originalObject, originalString, args);
        // 注意当一个msg被拆分时，只有在最后一次输出完全时才会传递原始参数，前面都会传null
        for (int i = 0; i < splitMsg.length; i++) {
            String[] lines = splitMsg[i].split(System.getProperty("line.separator"));
            for (int j = 0; j < lines.length; j++) {
                if (j != lines.length - 1 || i != splitMsg.length - 1) {
                    dispatchLog(priority, tag, t, null, getLineCompoundStr(lines[j]), null);
                } else {
                    dispatchLog(priority, tag, t, originalObject, getLineCompoundStr(lines[j]), originalString, args);
                }
            }
        }
    }

    private void dispatchLog(int priority, String tag, Throwable t, Object originalObject, String compoundMsg, String
            originalString, Object... args) {
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
            tag = createCompoundTag(tag);
        } else {
            tag = setting.getPrefixTag();
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

    private boolean isShowThreadInfo() {
        Boolean showThreadInfo = localShowThreadInfo.get();
        if (showThreadInfo != null) {
            localShowThreadInfo.remove();
        } else {
            showThreadInfo = setting.isShowThreadInfo();
        }
        return showThreadInfo;
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

    private String createCompoundTag(String srcTag) {
        return setting.getPrefixTag() + "-" + srcTag;
    }

    private String getLineCompoundStr(String line) {
        return HORIZONTAL_DOUBLE_LINE + " " + line;
    }

    private boolean isLoggable(int priority) {
        return priority >= setting.logLevel();
    }

    private boolean isLegalPriority(int priority) {
        return priority > FULL && priority < NONE;
    }
}
