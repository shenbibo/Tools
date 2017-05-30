package com.sky.tools.log.parse;

import com.sky.tools.log.ObjectParse;

/**
 * 对象解析接口，返回解析的结果为String
 * [实现该接口的必须要有一个默认构造函数，否则会无法解析]
 * Created by sky on 2017/5/27.
 */

public interface Parse<T> {
    Class<T> getParseType();

    /**
     * @param t, 要解析的数据类型
     * */
    String parseToString(T t);
}
