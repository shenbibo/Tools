package com.sky.tools.log;

import com.sky.tools.log.parse.Parse;

import java.util.concurrent.CopyOnWriteArrayList;

import static com.sky.tools.log.LogConstant.OBJECT_NULL_STRING;

/**
 * [解析对象]
 * [详述类的功能。]
 * Created by sky on 2017/5/28.
 */

public final class ObjectParse {
    private static final CopyOnWriteArrayList<Parse> parseObjects = new CopyOnWriteArrayList<>();

    static void addParseObject(Parse parseAdapter) {
        if (parseAdapter == null) {
            return;
        }

        parseObjects.addIfAbsent(parseAdapter);
    }

    static void removeParseObject(Parse parseAdapter) {
        parseObjects.remove(parseAdapter);
    }

    static String objectToString(Object object) {
        if(object == null){
            return OBJECT_NULL_STRING;
        }

        if(object instanceof String){
            return (String)object;
        }

        Parse parse = null;
        Class<?> clazz = object.getClass();
        // 先检测是否有确切的class类型的解析适配器
        if ((parse = getActualParse(clazz)) != null) {
            return parse.parseToString(object);
        }

        // 再检查是否有其超类的解析适配器
        if ((parse = getAssignableParse(clazz)) != null) {
            return parse.parseToString(object);
        }

        return object.toString();
    }

    /**
     * 从适配器表中获取确切的与给定对象class相等的解析器，不包括其超类的
     * */
    private static Parse<?> getActualParse(Class<?> clazz){
        for (Parse<?> parse : parseObjects){
            if(clazz == parse.getParseType()){
                return parse;
            }
        }
        return null;
    }

    /**
     * 从适配器表中获取确切的与给定对象class，或是其超类的解析器
     */
    private static Parse<?> getAssignableParse(Class<?> clazz) {
        for (Parse<?> parse : parseObjects){
            if(parse.getParseType().isAssignableFrom(clazz)){
                return parse;
            }
        }
        return null;
    }

}
