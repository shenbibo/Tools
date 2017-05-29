package com.sky.tools.log;

import com.sky.tools.log.LogManager.LogManagerImpl;

/**
 * 对象创建工具类
 * Created by sky on 2017/5/29.
 */
final class LogFactory {
    static LogController createLogController(){
        return new LogControllerImpl();
    }

    static LogManager createLogManager(){
        return new LogManagerImpl();
    }
}
