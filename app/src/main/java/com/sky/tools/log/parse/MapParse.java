package com.sky.tools.log.parse;

import java.util.Map;

/**
 * [一句话描述类的作用]
 * [详述类的功能。]
 * Created by sky on 2017/5/28.
 */

public class MapParse implements Parse<Map> {
    @Override
    public Class<Map> getParseType() {
        return Map.class;
    }

    @Override
    public String parseToString(Map map) {
        return null;
    }
}
