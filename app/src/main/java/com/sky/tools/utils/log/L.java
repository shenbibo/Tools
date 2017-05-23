package com.sky.tools.utils.log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

/**
 * 日志工具类
 * [detail]
 * Created by Sky on 2017/5/23.
 */

public final class L {
    /**
     * 默认日志TAG前缀
     */
    private static final String DEFAULT_PREFIX_TAG = "Android";

    private static final L INSTANCE = new L();

    private Setting mSetting;

    public static Setting init(){
        return init(DEFAULT_PREFIX_TAG);
    }

    public static Setting init(String prefixTag) {
        String tag = (prefixTag == null ? DEFAULT_PREFIX_TAG : prefixTag);
        INSTANCE.mSetting = new Setting(Logger.init(tag));
        return INSTANCE.mSetting;
    }
}
