package com.sky.tools.log.parse;

/**
 * [一句话描述类的作用]
 * [详述类的功能。]
 * Created by sky on 2017/5/28.
 */

public class ArrayParse implements Parse<Object[]> {
    @Override
    public Class<Object[]> getParseType() {
        return Object[].class;
    }

    @Override
    public String parseToString(Object[] objects) {
        return null;
    }
}
