package com.sky.tools.log.parse;

import com.sky.tools.log.ParseObject;
import com.sky.tools.log.Slog;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.sky.tools.log.LogConstant.OBJECT_NULL_STRING;

/**
 * [一句话描述类的作用]
 * [详述类的功能。]
 * Created by sky on 2017/5/28.
 */

public class ArrayParser implements Parser<Object[]> {
    @Override
    public Class<Object[]> getParseType() {
        return Object[].class;
    }

    @Override
    public String parseToString(Object[] objects) {
        return deepToString(objects);
    }

    private static String deepToString(Object[] a) {
        if (a == null) {
            return OBJECT_NULL_STRING;
        }

        Slog.i("called the ArrayParser!!!!!");

        int bufLen = 20 * a.length;
        if (a.length != 0 && bufLen <= 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuilder buf = new StringBuilder(bufLen);
        deepToString(a, buf, new HashSet<Object[]>());
        return buf.toString();
    }

    private static void deepToString(Object[] a, StringBuilder buf, Set<Object[]> dejaVu) {
        if (a == null) {
            buf.append(OBJECT_NULL_STRING);
            return;
        }

        int iMax = a.length - 1;
        if (iMax == -1) {
            buf.append("[]");
            return;
        }

        dejaVu.add(a);
        buf.append('[');
        for (int i = 0; ; i++) {

            Object element = a[i];
            if (element == null) {
                buf.append(OBJECT_NULL_STRING);
            } else {
                Class eClass = element.getClass();

                if (eClass.isArray()) {
                    if (eClass == byte[].class) {
                        buf.append(Arrays.toString((byte[]) element));
                    } else if (eClass == short[].class) {
                        buf.append(Arrays.toString((short[]) element));
                    } else if (eClass == int[].class) {
                        buf.append(Arrays.toString((int[]) element));
                    } else if (eClass == long[].class) {
                        buf.append(Arrays.toString((long[]) element));
                    } else if (eClass == char[].class) {
                        buf.append(Arrays.toString((char[]) element));
                    } else if (eClass == float[].class) {
                        buf.append(Arrays.toString((float[]) element));
                    } else if (eClass == double[].class) {
                        buf.append(Arrays.toString((double[]) element));
                    } else if (eClass == boolean[].class) {
                        buf.append(Arrays.toString((boolean[]) element));
                    } else { // element is an array of object references
                        if (dejaVu.contains(element)) {
                            buf.append("[...]");
                        } else {
                            //                            deepToString((Object[]) element, buf, dejaVu);
                            buf.append(ParseObject.objectToString(element));
                        }
                    }
                } else {  // element is non-null and not an array
                    //                    buf.append(element.toString());
                    buf.append(ParseObject.objectToString(element));
                }
            }
            if (i == iMax) {
                break;
            }
            buf.append(", ");
        }
        buf.append(']');
        dejaVu.remove(a);
    }
}
