package com.sky.tools.utils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Random Utils
 * <p>
 * get random int
 * <li>{@link #getRandom(int)} get random int between 0 and max</li>
 * <li>{@link #getRandom(int, int)} get random int between min and max</li>
 * </ul>
 * <ul>
 * get random numbers or letters
 * <li>{@link #getRandomCapitalLetters(int)} get a fixed-length random string, its a mixture of uppercase letters</li>
 * <li>{@link #getRandomLetters(int)} get a fixed-length random string, its a mixture of uppercase and lowercase letters
 * </li>
 * <li>{@link #getRandomLowerCaseLetters(int)} get a fixed-length random string, its a mixture of lowercase letters</li>
 * <li>{@link #getRandomNumbers(int)} get a fixed-length random string, its a mixture of numbers</li>
 * <li>{@link #getRandomNumbersAndLetters(int)} get a fixed-length random string, its a mixture of uppercase, lowercase
 * letters and numbers</li>
 * <li>{@link #getRandom(String, int)} get a fixed-length random string, its a mixture of chars in source</li>
 * <li>{@link #getRandom(char[], int)} get a fixed-length random string, its a mixture of chars in sourceChar</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-12
 */
public class RandomUtils {

    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    private RandomUtils() {
        throw new AssertionError();
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase, lowercase letters and numbers
     *
     * @param length
     * @return
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of numbers
     *
     * @param length
     * @return
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of uppercase and lowercase letters
     *
     * @param length
     * @return
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    /**
     * 获取由大写字母组成的随机字符
     *
     * @param length
     * @return
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    /**
     * 获取由小些字母组成的随机字符
     *
     * @param length
     * @return
     * @see RandomUtils#getRandom(String source, int length)
     */
    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in source
     *
     * @param source
     * @param length
     * @return <ul>
     * <li>if source is null or empty, return null</li>
     * <li>else see {@link RandomUtils#getRandom(char[] sourceChar, int length)}</li>
     * </ul>
     */
    public static String getRandom(String source, int length) {
        return StringUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    /**
     * get a fixed-length random string, its a mixture of chars in sourceChar
     *
     * @param sourceChar
     * @param length
     * @return <ul>
     * <li>if sourceChar is null or empty, return null</li>
     * <li>if length less than 0, return null</li>
     * </ul>
     */
    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    /**
     * get random int between 0 and max
     *
     * @param max
     * @return <ul>
     * <li>if max <= 0, return 0</li>
     * <li>else return random int between 0 and max</li>
     * </ul>
     */
    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    /**
     * get random int between min and max
     *
     * @param min
     * @param max 不包含在可取的随机数范围之内
     * @return <ul>
     * <li>if min > max, return 0</li>
     * <li>if min == max, return min</li>
     * <li>else return random int between min and max</li>
     * </ul>
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    public static long getRandomLong() {
        return new Random().nextLong();
    }


    /**
     * 从数组中随机不重复的抽取N个元素
     *
     * @param source
     * @param shuffleCount
     * @return T[] 返回从source中随机抽取的shuffleCount个元素的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] shuffle(T[] source, int shuffleCount) {
        if (source == null || shuffleCount < 0 || source.length < shuffleCount) {
            return null;
        }

        List<T> temp = Arrays.asList(source);
        List<T> out = shuffle(temp, shuffleCount);

        return out.toArray((T[]) Array.newInstance(source.getClass().getComponentType(), shuffleCount));
    }

    public static <T> List<T> shuffle(List<T> source, int shuffleCount) {
        if (source == null || shuffleCount < 0 || source.size() < shuffleCount) {
            return null;
        }

        List<T> out = new ArrayList<>();
        List<T> temp = new ArrayList<>();
        temp.addAll(source);

        // 采用的算法原理是，每次得出一个随机值后，取该位置的元素放入out中，并将随机数Max-1
        // 并且将随机值位置的元素设置为倒序递减位置的元素，防止重复
        int pos = temp.size();
        for (int i = 0; i < shuffleCount; i++) {
            int random = getRandom(pos--);
            out.add(i, temp.get(random));
            temp.set(random, temp.get(pos));
        }
        return out;
    }

    /**
     * 生成给定返范围内随机互不相同的数组
     * min 和 max 都在可取范围之内
     * size < 0 || min < 0 || max < 0 || max <= min || (size > (max - min)) 时 返回 new int[0];
     */
    public static int[] createMutexRandomArray(int min, int max, int size) {
        if (size <= 0 || min < 0 || max < 0 || max < min || (size > (max - min))) {
            return new int[0];
        }

        int[] result;
        int randomLen = max - min;
        // 当可取随机数的范围小于等于10W时使用生成随机数组方式
        if (randomLen <= 100000) {
            result = getMutexRandomBySourceArray(min, max, size, randomLen);
        }else{
            result = getMutexRandomByHashSet(min, max, size);
        }

        return result;
    }

    /**
     * 将随机数取值范围设置为一个数组，通过生成随机数取出数据源中的数据作为返回结果中的随机数
     * 当max - min 非常大时存在性能问题，甚至出现OOM
     * */
    private static int[] getMutexRandomBySourceArray(int min, int max, int size, int randomLen) {
        int[] result = new int[size];
        int[] randomSource = new int[randomLen];
        // 当max和min之差很大时，此处存在性能问题，甚至出现OOM
        for (int i = min; i < max; i++) {
            randomSource[i - min] = i;
        }

        int index;
        Random d = new Random();
        int swapIndex = randomLen;

        for (int i = 0; i < size; i++) {
            index = d.nextInt(swapIndex--);
            result[i] = randomSource[index];
            randomSource[index] = randomSource[swapIndex];
        }

        return result;
    }

    private static int[] getMutexRandomByHashSet(int min, int max, int size) {
        HashSet<Integer> randSet = new HashSet<>();
        createMutexRandomToHashSet(min, max, size, randSet);

        Integer[] tempResult = randSet.toArray(new Integer[0]);
        int[] result = new int[tempResult.length];
        System.arraycopy(tempResult, 0, result, 0, tempResult.length);

        return result;
    }

    /**
     * 利用hashSet的不可重复性，存储随机元素，直到最后存满
     * */
    private static void createMutexRandomToHashSet(int min, int max, int size, HashSet<Integer> randSet) {
        Random r = new Random();
        for (int i = 0; i < size; i++){
            int random = min + r.nextInt(max - min);
            randSet.add(random);
        }

        if(randSet.size() < size){
            createMutexRandomToHashSet(min, max, size - randSet.size(), randSet);
        }
    }

    // test code
    public static void main(String[] args) {
        Integer[] testSource = {1, 23, 5, 6, 7, 9, 10, 11, 12, 14, 16};
        Integer[] out = shuffle(testSource, 5);
        for (Integer i : out) {
            System.out.println(i);
        }

        System.out.println("list test");
        List<Integer> inter = new ArrayList<>();
        inter.add(1);
        inter.add(2);
        inter.add(3);
        inter.add(4);
        inter.add(5);
        inter.add(6);
        inter.add(7);
        inter.add(8);
        inter.add(9);
        List<Integer> outList = shuffle(inter, 4);
        for (Integer value : outList) {
            System.out.println(value);
        }
    }
}
