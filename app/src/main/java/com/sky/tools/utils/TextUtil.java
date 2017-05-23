package com.sky.tools.utils;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.regex.Pattern;

/**
 * 字符串工具类，其内部大部分方法，只是封装调用了{@link TextUtils}类的方法。
 */
public class TextUtil {
    /**
     * 默认分割符，防止出现重复
     * */
    static final String JOIN_DEFAULT_SPILT = "2,天0;1工@=#7,p具;s:0项5=19";

    /**
     * is null or its length is 0 or it is made by space
     * <p>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <p>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    /**
     * get length of CharSequence
     * <p>
     * <pre>
     * length(null) = 0;
     * </pre>
     *
     * @param str
     * @return if str is null or empty, return 0, else return {@link CharSequence#length()}.
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * null Object to empty string
     * <p>
     * <pre>
     * toString(null) = &quot;&quot;;
     * toString(&quot;&quot;) = &quot;&quot;;
     * toString(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }

    /**
     * 首字母大写
     * <p>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * 将字符从此字符串复制到目标字符数组。
     * <p>
     * 要复制的第一个字符位于索引 srcBegin 处；要复制的最后一个字符位于索引 srcEnd-1 处（因此要复制的字符总数是 srcEnd-srcBegin）。要复制到 dst 子数组的字符从索引 dstBegin 处开始，并结束于索引：
     * dstbegin + (srcEnd-srcBegin) - 1
     */
    public static void getChars(CharSequence s, int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        TextUtils.getChars(s, srcBegin, srcEnd, dst, dstBegin);
    }

    public static int indexOf(CharSequence s, char ch) {
        return TextUtils.indexOf(s, ch);
    }

    public static int indexOf(CharSequence s, char ch, int start) {
        return TextUtils.indexOf(s, ch, start);
    }

    public static int indexOf(CharSequence s, char ch, int start, int end) {
        return TextUtils.indexOf(s, ch, start, end);
    }

    public static int lastIndexOf(CharSequence s, char ch) {
        return TextUtils.lastIndexOf(s, ch);
    }

    public static int lastIndexOf(CharSequence s, char ch, int last) {
        return TextUtils.lastIndexOf(s, ch, last);
    }

    /**
     * 在[start, last]之间最后一次出现ch的index
     */
    public static int lastIndexOf(CharSequence s, char ch, int start, int last) {
        return TextUtils.lastIndexOf(s, ch, start, last);
    }

    /**
     * 返回字符串中包含指定字符串的起始位置
     */
    public static int indexOf(CharSequence s, CharSequence needle) {
        return TextUtils.indexOf(s, needle);
    }

    public static int indexOf(CharSequence s, CharSequence needle, int start) {
        return TextUtils.indexOf(s, needle, start);
    }

    public static int indexOf(CharSequence s, CharSequence needle, int start, int end) {
        return TextUtil.indexOf(s, needle, start, end);
    }

    /**
     * 测试两个字符串的指定区域的字符串是否相等
     */
    public static boolean regionMatches(CharSequence one, int toffset, CharSequence two, int ooffset, int len) {
        return TextUtils.regionMatches(one, toffset, two, ooffset, len);
    }

    /**
     * 不同与简单的返回字符串，如果source 不是String, stringBuffer, stringBuilder 可能会将临时生成的char数组缓存
     * 以提供更高的效率。
     */
    public static String substring(CharSequence source, int start, int end) {
        return TextUtils.substring(source, start, end);
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     * 插入字符串
     * <p>
     * 最终的字符串样式类似于 Token1DelimiterToken2Delimiter.....
     *
     * @param delimiter 界定符，插入在token之后
     * @param tokens    an array objects to be joined. Strings will be formed from
     *                  the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Object[] tokens) {
        return TextUtils.join(delimiter, tokens);
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     * 最终的字符串样式类似于 Token1DelimiterToken2Delimiter.....
     *
     * @param delimiter 界定符，插入在tokenDelimiter....位置
     * @param tokens    an array objects to be joined. Strings will be formed from
     *                  the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Iterable tokens) {
        return TextUtils.join(delimiter, tokens);
    }

    /**
     * String.split() returns [''] when the string to be split is empty. This returns []. This does
     * not remove any empty strings from the result. For example split("a,", ","  ) returns {"a", ""}.
     *
     * @param text       the string to split
     * @param expression the regular expression to match
     * @return an array of strings. The array will be empty if text.length = 0
     * @throws NullPointerException if expression or text is null
     */
    public static String[] split(String text, String expression) {
        return TextUtils.split(text, expression);
    }

    /**
     * Splits a string on a pattern. String.split() returns [''] when the string to be
     * split is empty. This returns []. This does not remove any empty strings from the result.
     *
     * @param text    the string to split
     * @param pattern the regular expression to match
     * @return an array of strings. The array will be empty if text.length = 0
     * @throws NullPointerException if expression or text is null
     */
    public static String[] split(String text, Pattern pattern) {
        return TextUtils.split(text, pattern);
    }

    /**
     * 返回字符串中去除首尾ASCII码中前32个控制字符（不可打印）和空格之后的字符串长度
     * 类似于{@link String#trim}.
     */
    public static int getTrimmedLength(CharSequence s) {
        return TextUtils.getTrimmedLength(s);
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     *
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        return TextUtils.equals(a, b);
    }

    /**
     * Flatten a CharSequence and whatever styles can be copied across processes
     * into the parcel.
     *
     * @param parcelableFlags Additional flags about how the object should be written.
     *                        May be 0 or {@link android.os.Parcelable#PARCELABLE_WRITE_RETURN_VALUE}.
     */
    public static void writeToParcel(CharSequence cs, Parcel p, int parcelableFlags) {
        TextUtils.writeToParcel(cs, p, parcelableFlags);
    }

    /**
     * 将模板字符串中的某些想替换的字符串替换为指定的字符串，如：
     * {@code
     * template="abcdefghig";
     * source={"a","de"};
     * destinations={"wxjj", "1234"};
     * return "wxjjbc1234fghig";
     * }
     */
    public static CharSequence replace(CharSequence template, String[] sources, CharSequence[] destinations) {
        return TextUtils.replace(template, sources, destinations);
    }

    /**
     * Replace instances of "^1", "^2", etc. in the
     * <code>template</code> CharSequence with the corresponding
     * <code>values</code>.  "^^" is used to produce a single caret in
     * the output.  Only up to 9 replacement values are supported,
     * "^10" will be produce the first replacement value followed by a
     * '0'.
     * <p></p>
     * ^^等于插入^符号
     *
     * @param template the input text containing "^1"-style
     *                 placeholder values.  This object is not modified; a copy is
     *                 returned.
     * @param values   CharSequences substituted into the template.  The
     *                 first is substituted for "^1", the second for "^2", and so on.
     * @return the new CharSequence produced by doing the replacement
     * @throws IllegalArgumentException if the template requests a
     *                                  value that was not provided, or if more than 9 values are
     *                                  provided.
     */
    public static CharSequence expandTemplate(CharSequence template,
            CharSequence... values) {
        return TextUtils.expandTemplate(template, values);
    }

    /**
     * 将普通字符串编码成html字符串
     *
     * @param s the string to be encoded
     * @return the encoded string
     */
    public static String htmlEncode(String s) {
        return TextUtils.htmlEncode(s);
    }

    /**
     * 按序拼接字符串，并保留原来的每个字符串的格式（如颜色，富文本等）
     */
    public static CharSequence concat(CharSequence... text) {
        return TextUtils.concat(text);
    }

    /**
     * 字符串是否包含有可以打印的字符（即可以显示）
     * */
    public static boolean isGraphic(CharSequence str) {
        return TextUtils.isGraphic(str);
    }

    /**
     * 字符串是否由纯数字组成
     * */
    public static boolean isDigitsOnly(CharSequence str) {
        return TextUtils.isDigitsOnly(str);
    }

}
