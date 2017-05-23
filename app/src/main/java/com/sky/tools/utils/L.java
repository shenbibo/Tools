package com.sky.tools.utils;

import com.orhanobut.logger.Logger;

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

    public static L init(String prefixTag){
        String tag = (prefixTag == null ? DEFAULT_PREFIX_TAG : prefixTag);
        Logger.init(tag);
        return INSTANCE;
    }
}
