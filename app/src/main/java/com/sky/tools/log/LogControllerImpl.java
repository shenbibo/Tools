package com.sky.tools.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.sky.tools.log.Helper.*;
import static com.sky.tools.log.Slog.*;
import static com.sky.tools.log.LogConstant.*;

/**
 * [日志中间处理转发类]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

class LogControllerImpl extends LogController {
    /**
     * 最小的栈偏移值，因为该类本身和包裹它的类，所以默认偏移2
     */
    private static final int MIN_STACK_OFFSET = 6;

    private Setting setting;
    private Class<?> callerClass;
    private LogDispatcher dispatcher;

    private ThreadLocal<String> localTag = new ThreadLocal<>();
    private ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();
    private ThreadLocal<Boolean> localSimpleMode = new ThreadLocal<>();
    private ThreadLocal<Boolean> localShowThreadInfo = new ThreadLocal<>();
    private ThreadLocal<Integer> localMethodOffset = new ThreadLocal<>();

    @Override
    public void v(String msg, @Nullable Object... args) {
        log(VERBOSE, null, null, msg, args);
    }

    @Override
    public void d(String msg, @Nullable Object... args) {
        log(DEBUG, null, null, msg, args);
    }

    @Override
    public void d(Object object) {
        object(DEBUG, null, object);
    }

    @Override
    public void i(String msg, @Nullable Object... args) {
        log(INFO, null, null, msg, args);
    }

    @Override
    public void i(Object object) {
        object(INFO, null, object);
    }

    @Override
    public void w(String msg, @Nullable Object... args) {
        log(WARN, null, null, msg, args);
    }

    @Override
    public void w(Throwable t, String msg, @Nullable Object... args) {
        log(WARN, null, t, msg, args);
    }

    @Override
    public void e(String msg, @Nullable Object... args) {
        log(ERROR, null, null, msg, args);
    }

    @Override
    public void e(Throwable t, String msg, @Nullable Object... args) {
        log(ERROR, null, t, msg, args);
    }

    @Override
    public void wtf(String msg, @Nullable Object... args) {
        log(ASSERT, null, null, msg, args);
    }

    @Override
    public void wtf(Throwable t, String msg, @Nullable Object... args) {
        log(ASSERT, null, t, msg, args);
    }

    @Override
    public void log(int priority, @Nullable String tag, @Nullable Throwable t, @Nullable String msg, @Nullable Object... args) {
        logParse(priority, tag, t, msg, args);
    }

    @Override
    public void object(int priority, String tag, Object object) {
        logParse(priority, tag, null, object);
    }

    @Override
    public void json(String json) {
        logParse(DEBUG, null, null, covertJson(json));
    }

    @Override
    public void xml(String xml) {
        logParse(DEBUG, null, null, covertXml(xml));
    }

    @Override
    public LogController t(String tag) {
        if (tag != null) {
            localTag.set(tag);
        }
        return this;
    }

    @Override
    public LogController m(Integer methodCount) {
        if (methodCount != null) {
            localMethodCount.set(methodCount);
        }
        return this;
    }

    @Override
    public LogController s(Boolean simpleMode) {
        if (simpleMode != null) {
            localSimpleMode.set(simpleMode);
        }
        return this;
    }

    @Override
    public LogController th(Boolean showThreadInfo) {
        if (showThreadInfo != null) {
            localShowThreadInfo.set(showThreadInfo);
        }
        return this;
    }

    @Override
    public LogController o(Integer methodOffset) {
        if(methodOffset != null){
            localMethodOffset.set(methodOffset);
        }
        return this;
    }

    @Override
    void init(@NonNull Class<?> callerClass, @NonNull Setting logSetting, @NonNull LogDispatcher logDispatcher) {
        this.callerClass = callerClass;
        setting = logSetting;
        dispatcher = logDispatcher;
    }

    private void logParse(int priority, String tag, Throwable t, Object originalObject, Object... args) {
        if (!isLoggable(priority) || !isLegalPriority(priority)) {
            return;
        }

        if (originalObject == null) {
            return;
        }

        String finalTag = tag != null ? createCompoundTag(tag) : getTag();
        // 简单模式不组装消息直接返回
        if (isSimpleMode()) {
            logSimpleMode(priority, finalTag, t, originalObject, args);
            return;
        }

        logStandardMode(priority, finalTag, t, originalObject, args);
    }

    private void logSimpleMode(int priority, String tag, Throwable t, Object originalObject, Object... args) {
        String[] spiltMsg = compoundMsgAndSplit(t, originalObject, args);
        for (int i = 0; i < spiltMsg.length; i++) {
            // 注意当一个msg被拆分时，只有在最后一次输出完全时才会传递原始参数，前面都会传null
            if (i != spiltMsg.length - 1) {
                dispatchLog(priority, tag, t, spiltMsg[i], null);
            } else {
                dispatchLog(priority, tag, t, spiltMsg[i], originalObject, args);
            }
        }
    }

    private synchronized void logStandardMode(int priority, String tag, Throwable t, Object originalObject, Object... args) {
        int methodCount = getMethodCount();
        logTopBorder(priority, tag);
        logHeaderContent(priority, tag, methodCount);

        if (methodCount > 0) {
            logDivider(priority, tag);
        }

        logContent(priority, tag, t, originalObject, args);
        logBottomBorder(priority, tag);
    }

    /** 组装格式化字符串或解析对象，并且如果长度超过限制则进行切除 */
    private String[] compoundMsgAndSplit(Throwable t, Object originalObject, Object... args) {
        String compoundMsg;
        if (originalObject instanceof String) {
            compoundMsg = formatMessage((String) originalObject, args);
        } else {
            compoundMsg = parseObject(originalObject);
        }

        if (t != null) {
            compoundMsg += " : " + getStackTraceString(t);
        }

        return splitString(compoundMsg);
    }


    private void logTopBorder(int priority, String tag) {
        dispatchLog(priority, tag, null, TOP_BORDER, null);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void logHeaderContent(int priority, String tag, int methodCount) {
        Thread curThread = Thread.currentThread();
        StackTraceElement[] trace = curThread.getStackTrace();
        if (isShowThreadInfo()) {
            String threadInfo = createThreadInfo(curThread);
            dispatchLog(priority, tag, null, getLineCompoundStr(threadInfo), null);
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
            dispatchLog(priority, tag, null, builder.toString(), null);
        }
    }

    private void logBottomBorder(int priority, String tag) {
        dispatchLog(priority, tag, null, BOTTOM_BORDER, null);
    }

    private void logDivider(int priority, String tag) {
        dispatchLog(priority, tag, null, MIDDLE_BORDER, null);
    }

    private void logContent(int priority, String tag, Throwable t, Object originalObject, Object... args) {
        String[] splitMsg = compoundMsgAndSplit(t, originalObject, args);
        // 注意当一个msg被拆分时，只有在最后一次输出完全时才会传递原始参数，前面都会传null
        for (int i = 0; i < splitMsg.length; i++) {
            String[] lines = splitMsg[i].split(LINE_SEPARATOR);
            for (int j = 0; j < lines.length; j++) {
                if (j != lines.length - 1 || i != splitMsg.length - 1) {
                    dispatchLog(priority, tag, t, getLineCompoundStr(lines[j]), null);
                } else {
                    dispatchLog(priority, tag, t, getLineCompoundStr(lines[j]), originalObject, args);
                }
            }
        }
    }

    private void dispatchLog(int priority, String tag, Throwable t, String compoundMsg, Object originalObject, Object... args) {
        if (originalObject instanceof String) {
            dispatcher.log(priority, tag, t, compoundMsg, (String) originalObject, args);
        } else {
            dispatcher.log(priority, tag, compoundMsg, originalObject);
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

    private int getStackOffset(StackTraceElement[] stackTraceElements){
        Integer userSetOffset = localMethodOffset.get();
        if (userSetOffset != null) {
            localMethodOffset.remove();
        } else {
            userSetOffset = setting.getMethodOffset();
        }

       return userSetOffset + getMinStackOffset(stackTraceElements);
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
    private int getMinStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(this.getClass().getName()) && !name.equals(callerClass.getName())) {
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
        return priority >= setting.getLogPriority();
    }

    private boolean isLegalPriority(int priority) {
        return priority > FULL && priority < NONE;
    }
}
