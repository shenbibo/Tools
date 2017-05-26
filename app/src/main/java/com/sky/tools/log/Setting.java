package com.sky.tools.log;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/25.
 */

public class Setting {
    private int methodCount = 1;
    private int logLevel = Slog.FULL;
    private boolean showThreadInfo = false;
    /** 简单模式，设置为true之后和正常的普通log一样 */
    private boolean simpleMode = false;
    private String defaultTag = Slog.DEFAULT_TAG;


    public int getMethodCount() {
        return methodCount;
    }

    public Setting methodCount(int methodCount) {
        this.methodCount = methodCount < 0 ? 0 : methodCount;
        return this;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public Setting showThreadInfo(boolean showThreadInfo) {
        this.showThreadInfo = showThreadInfo;
        return this;
    }

    public int logLevel() {
        return logLevel;
    }

    public Setting setLogLevel(int logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public String defaultTag() {
        return defaultTag;
    }

    public Setting setDefaultTag(String defaultTag) {
        if(defaultTag != null){
            this.defaultTag = defaultTag;
        }
        return this;
    }

    public boolean isSimpleMode() {
        return simpleMode;
    }

    /**
     * 如果设置该值为true，则{@link Setting#methodCount},{@link Setting#showThreadInfo},字段不再生效
     * */
    public Setting simpleMode(boolean simpleMode) {
        this.simpleMode = simpleMode;
        return this;
    }
}
