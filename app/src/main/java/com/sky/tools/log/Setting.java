package com.sky.tools.log;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

public class Setting {
    private int methodCount = 1;
    private int logLevel = Slog.FULL;
    private boolean hideThreadInfo = true;
    /** 简单模式，设置为true之后和正常的普通log一样 */
    private boolean simpleMode = false;
    private String defaultTag = Slog.DEFAULT_TAG;


    public int getMethodCount() {
        return methodCount;
    }

    public Setting setMethodCount(int methodCount) {
        this.methodCount = methodCount;
        return this;
    }

    public boolean isHideThreadInfo() {
        return hideThreadInfo;
    }

    public Setting setHideThreadInfo(boolean hideThreadInfo) {
        this.hideThreadInfo = hideThreadInfo;
        return this;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public Setting setLogLevel(int logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public String getDefaultTag() {
        return defaultTag;
    }

    public Setting setDefaultTag(String defaultTag) {
        this.defaultTag = defaultTag;
        return this;
    }

    public boolean isSimpleMode() {
        return simpleMode;
    }

    /**
     * 如果设置该值为true，则{@link Setting#methodCount},{@link Setting#hideThreadInfo},字段不再生效
     * */
    public Setting setSimpleMode(boolean simpleMode) {
        this.simpleMode = simpleMode;
        return this;
    }
}
