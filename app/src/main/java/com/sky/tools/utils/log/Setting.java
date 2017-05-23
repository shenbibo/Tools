package com.sky.tools.utils.log;

import com.orhanobut.logger.*;

/**
 * [日志配置类]
 * [详述类的功能。]
 * Created by sky on 2017/5/24.
 */

public class Setting {
    private Settings mSettings;
    private LogLevel mLogLevel = LogLevel.FULL;
    Setting(Settings settings){
        mSettings = settings;
        // 默认初始化的时候就设置logger层的日志全部打印
        // 上层返回的实际是重新定义的LogLevel
        mSettings.logLevel(com.orhanobut.logger.LogLevel.FULL);
    }

    public Setting hideThreadInfo() {
        mSettings.hideThreadInfo();
        return this;
    }

    public Setting methodCount(int methodCount) {
        mSettings.methodCount(methodCount);
        return this;
    }

    public Setting logLevel(LogLevel logLevel) {
        mLogLevel = logLevel;
        return this;
    }

    public Setting methodOffset(int offset) {
        mSettings.methodOffset(offset);
        return this;
    }

    Setting logAdapter(LogAdapter logAdapter) {
        mSettings.logAdapter(logAdapter);
        return this;
    }

    public int getMethodCount() {
        return mSettings.getMethodCount();
    }

    public boolean isShowThreadInfo() {
        return mSettings.isShowThreadInfo();
    }

    public LogLevel getLogLevel() {
        return mLogLevel;
    }

    public int getMethodOffset() {
        return mSettings.getMethodOffset();
    }

    public void reset() {
        mLogLevel = LogLevel.FULL;
        mSettings.reset();
    }
}
