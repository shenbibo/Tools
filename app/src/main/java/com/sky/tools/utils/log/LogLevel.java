package com.sky.tools.utils.log;

/**
 * [一句话描述类的作用]
 * [详述类的功能。]
 * Created by sky on 2017/5/24.
 */

public enum LogLevel {

    /**
     * All log
     * */
    FULL(1),

    /**
     * Priority constant for the println method; use Log.v.
     */
    VERBOSE(2),

    /**
     * Priority constant for the println method; use Log.d.
     */
    DEBUG(3),

    /**
     * Priority constant for the println method; use Log.i.
     */
    INFO(4),

    /**
     * Priority constant for the println method; use Log.w.
     */
    WARN(5),

    /**
     * Priority constant for the println method; use Log.e.
     */
    ERROR(6),

    /**
     * Priority constant for the println method.
     */
    ASSERT(7),

    /**
     * no log
     * */
    NONE(8);

    int mLevel;

    LogLevel(int level){
        mLevel = level;
    }

    public int getLevel(){
        return mLevel;
    }
}
