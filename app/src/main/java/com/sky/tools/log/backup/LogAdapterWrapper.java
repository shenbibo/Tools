package com.sky.tools.log.backup;

import org.json.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import timber.log.Timber;
import timber.log.Timber.Tree;

import static com.sky.tools.log.Slog.ASSERT;
import static com.sky.tools.log.Slog.VERBOSE;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/24.
 */

public class LogAdapterWrapper extends LogAdapter {
    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;
    static final LogAdapterWrapper LOG_ADAPTER = new LogAdapterWrapper();

    private final ThreadLocal<String> explicitTag = new ThreadLocal<>();
    private String defaultTag = "";

    private LogAdapterWrapper() {}

    /**
     * Log a verbose message with optional format args.
     */
    @Override
    public void v(String message, Object... args) {
        Timber.tag(getTag()).v(message, args);
    }

    /**
     * Log a verbose exception and a message with optional format args.
     */
    @Override
    public void v(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).v(t, message, args);
    }

    /**
     * Log a verbose exception.
     */
    @Override
    public void v(Throwable t) {
        Timber.tag(getTag()).v(t);
    }

    /**
     * Log a debug message with optional format args.
     */
    @Override
    public void d(String message, Object... args) {
        Timber.tag(getTag()).d(message, args);
    }

    /**
     * Log a debug exception and a message with optional format args.
     */
    @Override
    public void d(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).d(t, message, args);
    }

    /**
     * Log a debug exception.
     */
    @Override
    public void d(Throwable t) {
        Timber.tag(getTag()).d(t);
    }

    /**
     * Log an info message with optional format args.
     */
    @Override
    public void i(String message, Object... args) {
        Timber.tag(getTag()).i(message, args);
    }

    /**
     * Log an info exception and a message with optional format args.
     */
    @Override
    public void i(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).i(t, message, args);
    }

    /**
     * Log an info exception.
     */
    @Override
    public void i(Throwable t) {
        Timber.tag(getTag()).i(t);
    }

    /**
     * Log a warning message with optional format args.
     */
    @Override
    public void w(String message, Object... args) {
        Timber.tag(getTag()).w(message, args);
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    @Override
    public void w(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).w(t, message, args);
    }

    /**
     * Log a warning exception.
     */
    @Override
    public void w(Throwable t) {
        Timber.tag(getTag()).i(t);
    }

    /**
     * Log an error message with optional format args.
     */
    @Override
    public void e(String message, Object... args) {
        Timber.tag(getTag()).e(message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    @Override
    public void e(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).e(t, message, args);
    }

    /**
     * Log an error exception.
     */
    @Override
    public void e(Throwable t) {
        Timber.tag(getTag()).e(t);
    }

    /**
     * Log an assert message with optional format args.
     */
    @Override
    public void wtf(String message, Object... args) {
        Timber.tag(getTag()).wtf(message, args);
    }

    /**
     * Log an assert exception and a message with optional format args.
     */
    @Override
    public void wtf(Throwable t, String message, Object... args) {
        Timber.tag(getTag()).wtf(t, message, args);
    }

    /**
     * Log an assert exception.
     */
    @Override
    public void wtf(Throwable t) {
        Timber.tag(getTag()).wtf(t);
    }

    /**
     * Log at {@code priority} a message with optional format args.
     */
    @Override
    public void log(int priority, String message, Object... args) {
        if (isLegalPriority(priority)) {
            Timber.tag(getTag()).log(priority, message, args);
        }
    }

    /**
     * Log at {@code priority} an exception and a message with optional format args.
     */
    @Override
    public void log(int priority, Throwable t, String message, Object... args) {
        if (isLegalPriority(priority)) {
            Timber.tag(getTag()).log(priority, t, message, args);
        }
    }

    /**
     * Log at {@code priority} an exception
     */
    @Override
    public void log(int priority, Throwable t) {
        if (isLegalPriority(priority)) {
            Timber.tag(getTag()).log(priority, t);
        }
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        throw new RuntimeException("not support method");
    }

    /**
     * 打印对象如list,map,set array
     */
    public static void d(Object object) {
        String message;
        if (object.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) object);
        } else {
            message = object.toString();
        }
        d(message);
    }

    public void json(String json) {
        if (isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
                return;
            }
            e("Invalid Json");
        } catch (JSONException e) {
            e("Invalid Json");
        }
    }

    public void xml(String xml) {
        if (isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml");
        }
    }

    /**
     * 添加一个新的日志打印的适配器到日志打印器中
     */
    public void plant(Tree tree) {
        Timber.plant(tree);
    }

    /**
     * 移除指定的tree
     */
    public void removeLogAdapter(Tree tree) {
        Timber.uproot(tree);
    }

    /**
     * 移除所有的tree
     */
    public void removeAllLogAdapter() {
        Timber.uprootAll();
    }

    public void setDefaultTag(String tag) {
        defaultTag = tag;
    }

    /**
     * 设置Tag，用于给下一次调用log方法使用
     */
    public LogAdapterWrapper setTag(String tag) {
        if (tag != null) {
            explicitTag.set(tag);
        }
        return this;
    }

    private String getTag() {
        String tag = explicitTag.get();
        if (tag != null) {
            explicitTag.remove();
        } else {
            tag = defaultTag;
        }

        return tag;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private static boolean isLegalPriority(int priority) {
        return priority >= VERBOSE && priority <= ASSERT;
    }
}
