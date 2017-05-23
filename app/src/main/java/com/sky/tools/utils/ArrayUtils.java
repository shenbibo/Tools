package com.sky.tools.utils;

/**
 * Array Utils
 * <ul>
 * <li>{@link #isEmpty(Object[])} is null or its length is 0</li>
 * <li>{@link #getLatestEntryBeforeTargetValueFromEnd(Object[], Object, Object, boolean)} get last element of the target element, before the first one
 * that match the target element front to back</li>
 * <li>{@link #getLatestEntryAfterTargetValueFromEnd(Object[], Object, Object, boolean)} get next element of the target element, after the first one
 * that match the target element front to back</li>
 * <li>{@link #getLatestEntryBeforeTargetValueFromEnd(Object[], Object, boolean)}</li>
 * <li>{@link #getLatestEntryBeforeTargetValueFromEnd(int[], int, int, boolean)}</li>
 * <li>{@link #getLatestEntryBeforeTargetValueFromEnd(long[], long, long, boolean)}</li>
 * <li>{@link #getLatestEntryAfterTargetValueFromEnd(Object[], Object, boolean)}</li>
 * <li>{@link #getLatestEntryAfterTargetValueFromEnd(int[], int, int, boolean)}</li>
 * <li>{@link #getLatestEntryAfterTargetValueFromEnd(long[], long, long, boolean)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-10-24
 */
public class ArrayUtils {

    private ArrayUtils() {
        throw new AssertionError();
    }

    /**
     * is null or its length is 0
     * 
     * @param <V>
     * @param sourceArray
     * @return
     */
    public static <V> boolean isEmpty(V[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * get last element of the target element, before the first one that match the target element front to back
     * 返回匹配给定value值的前一个元素
     * <ul>
     * <li>if array is empty, return defaultValue</li>
     * <li>if target element is not exist in array, return defaultValue</li>
     * <li>if target element exist in array and its index is not 0, return the last element</li>
     * <li>if target element exist in array and its index is 0, return the last one in array if isCircle is true, else
     * return defaultValue</li>
     * </ul>
     * 
     * @param <V>
     * @param sourceArray
     * @param value value of target element
     * @param defaultValue default return value
     * @param isCircle whether is circle
     * @return
     */
    public static <V> V getLatestEntryBeforeTargetValueFromEnd(V[] sourceArray, V value, V defaultValue, boolean isCircle) {
        if (isEmpty(sourceArray)) {
            return defaultValue;
        }

        int currentPosition = -1;
        for (int i = 0; i < sourceArray.length; i++) {
            if (ObjectUtils.isEquals(value, sourceArray[i])) {
                currentPosition = i;
                break;
            }
        }
        if (currentPosition == -1) {
            return defaultValue;
        }

        if (currentPosition == 0) {
            return isCircle ? sourceArray[sourceArray.length - 1] : defaultValue;
        }
        return sourceArray[currentPosition - 1];
    }

    /**
     * get next element of the target element, after the first one that match the target element front to back
     * <ul>
     * <li>if array is empty, return defaultValue</li>
     * <li>if target element is not exist in array, return defaultValue</li>
     * <li>if target element exist in array and not the last one in array, return the next element</li>
     * <li>if target element exist in array and the last one in array, return the first one in array if isCircle is
     * true, else return defaultValue</li>
     * </ul>
     * 
     * @param <V>
     * @param sourceArray
     * @param value value of target element
     * @param defaultValue default return value
     * @param isCircle whether is circle
     * @return
     */
    public static <V> V getLatestEntryAfterTargetValueFromEnd(V[] sourceArray, V value, V defaultValue, boolean isCircle) {
        if (isEmpty(sourceArray)) {
            return defaultValue;
        }

        int currentPosition = -1;
        for (int i = 0; i < sourceArray.length; i++) {
            if (ObjectUtils.isEquals(value, sourceArray[i])) {
                currentPosition = i;
                break;
            }
        }
        if (currentPosition == -1) {
            return defaultValue;
        }

        if (currentPosition == sourceArray.length - 1) {
            return isCircle ? sourceArray[0] : defaultValue;
        }
        return sourceArray[currentPosition + 1];
    }

    /**
     * @see ArrayUtils#getLatestEntryBeforeTargetValueFromEnd(Object[], Object, Object, boolean) defaultValue is null
     */
    public static <V> V getLatestEntryBeforeTargetValueFromEnd(V[] sourceArray, V value, boolean isCircle) {
        return getLatestEntryBeforeTargetValueFromEnd(sourceArray, value, null, isCircle);
    }

    /**
     * @see ArrayUtils#getLatestEntryAfterTargetValueFromEnd(Object[], Object, Object, boolean) defaultValue is null
     */
    public static <V> V getLatestEntryAfterTargetValueFromEnd(V[] sourceArray, V value, boolean isCircle) {
        return getLatestEntryAfterTargetValueFromEnd(sourceArray, value, null, isCircle);
    }

    /**
     * @see ArrayUtils#getLatestEntryBeforeTargetValueFromEnd(Object[], Object, Object, boolean) Object is Long
     */
    public static long getLatestEntryBeforeTargetValueFromEnd(long[] sourceArray, long value, long defaultValue, boolean isCircle) {
        if (sourceArray.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }

        Long[] array = transformLongArray(sourceArray);
        return getLatestEntryBeforeTargetValueFromEnd(array, value, defaultValue, isCircle);

    }

    /**
     * @see ArrayUtils#getLatestEntryAfterTargetValueFromEnd(Object[], Object, Object, boolean) Object is Long
     */
    public static long getLatestEntryAfterTargetValueFromEnd(long[] sourceArray, long value, long defaultValue, boolean isCircle) {
        if (sourceArray.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }

        Long[] array = transformLongArray(sourceArray);
        return getLatestEntryAfterTargetValueFromEnd(array, value, defaultValue, isCircle);
    }

    /**
     * @see ArrayUtils#getLatestEntryBeforeTargetValueFromEnd(Object[], Object, Object, boolean) Object is Integer
     */
    public static int getLatestEntryBeforeTargetValueFromEnd(int[] sourceArray, int value, int defaultValue, boolean isCircle) {
        if (sourceArray.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }

        Integer[] array = transformIntArray(sourceArray);
        return getLatestEntryBeforeTargetValueFromEnd(array, value, defaultValue, isCircle);

    }

    /**
     * @see ArrayUtils#getLatestEntryAfterTargetValueFromEnd(Object[], Object, Object, boolean) Object is Integer
     */
    public static int getLatestEntryAfterTargetValueFromEnd(int[] sourceArray, int value, int defaultValue, boolean isCircle) {
        if (sourceArray.length == 0) {
            throw new IllegalArgumentException("The length of source array must be greater than 0.");
        }

        Integer[] array = transformIntArray(sourceArray);
        return getLatestEntryAfterTargetValueFromEnd(array, value, defaultValue, isCircle);
    }

    /**
     * convert long array to Long array
     *
     * @param source
     * @return
     */
    public static Long[] transformLongArray(long[] source) {
        Long[] dest = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        // 不能使用一下方法拷贝
        //        System.arraycopy(source, 0, dest, 0, source.length);
        return dest;
    }

    /**
     * convert Long array to long array
     *
     * @param source
     * @return
     */
    public static long[] transformLongArray(Long[] source) {
        long[] dest = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    /**
     * convert int array to Integer array
     *
     * @param source
     * @return
     */
    public static Integer[] transformIntArray(int[] source) {
        Integer[] dest = new Integer[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    /**
     * convert Integer array to int array
     *
     * @param source
     * @return
     */
    public static int[] transformIntArray(Integer[] source) {
        int[] dest = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }
}
