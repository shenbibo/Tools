package com.sky.tools.log.parse;

/**
 * 对象解析接口，返回解析的结果为String
 * [实现该接口的必须要有一个默认构造函数，否则会无法解析]
 * Created by sky on 2017/5/27.
 */

public interface Parse<T> {
    Class<T> getParseType();
    String parseToString(T t);
}
