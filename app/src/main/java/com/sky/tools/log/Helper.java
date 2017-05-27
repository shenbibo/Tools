package com.sky.tools.log;

import android.support.annotation.NonNull;

import com.sky.tools.log.parse.Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/25.
 */
class Helper {
    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * KEY = Object.getClass().getName();
     * value = Parse.getClass();
     * */
    private static final Map<String, Class<? extends Parse>> parseObjects = new ConcurrentHashMap<>();

    static String covertJson(String json) {
        if (isEmpty(json)) {
            return "Empty/Null json content";
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                return jsonObject.toString(JSON_INDENT);
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                return jsonArray.toString(JSON_INDENT);
            }
            return "Invalid Json";
        } catch (JSONException e) {
            return "Invalid Json";
        }
    }

    static String covertXml(String xml) {
        if (isEmpty(xml)) {
            return "Empty/Null xml content";
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            return "Invalid xml";
        }
    }

    static String parseObject(Object object) {
        if(parseObjects.containsKey(object.getClass().getName())){
            return parseSpecificObject(object);
        }

        return parseNormalObject(object);
    }

    private static String parseNormalObject(Object object) {
        String message;
        if (object.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) object);
        } else {
            message = object.toString();
        }
        return message;
    }

    private static String parseSpecificObject(Object object) {
        String message = object.toString();
        Class<? extends Parse> clazz = parseObjects.get(object.getClass().getName());
        if(clazz == null){
            return message;
        }

        try {
            Method method = clazz.getMethod("parseToString", Object.class);
            Parse parse = clazz.newInstance();
            message = (String) method.invoke(parse, object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 因为logcat对字数有限制4000，所以当字数大于4000时进行拆分成数组
     */
    static String[] splitString(@NonNull String string) {
        int length = string.length();
        if (length <= CHUNK_SIZE) {
            return new String[]{string};
        }

        String[] strings = new String[length / CHUNK_SIZE + 1];
        for (int i = 0, j = 0; i < length; i += CHUNK_SIZE, j++) {
            int count = Math.min(length - i, CHUNK_SIZE);
            strings[j] = string.substring(i, i + count);
        }
        return strings;
    }

    static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    static String formatMessage(String message, Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    static String getStackTraceString(Throwable t) {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
