package com.sky.tools.utils;

/**
 * ThreadUtils
 *
 */
public class ThreadUtils {


    /** recommend default thread pool size according to system available processors, {@link #getDefaultThreadPoolSize()} **/
    public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

    public static final int DEFAULT_MAX_THREAD_POOL_SIZE = 8;

    private ThreadUtils() {
        throw new AssertionError();
    }

    /**
     * get recommend default thread pool size
     * 
     * @return if 2 * availableProcessors + 1 less than 8, return it, else return 8;
     * @see {@link #getDefaultThreadPoolSize(int)} max is 8
     */
    public static int getDefaultThreadPoolSize() {
        return getDefaultThreadPoolSize(DEFAULT_MAX_THREAD_POOL_SIZE);
    }

    /**
     * get recommend default thread pool size
     * 
     * @param max
     * @return if availableProcessors + 1 less than max, return it, else return max;
     */
    public static int getDefaultThreadPoolSize(int max) {
        int availableProcessors = Runtime.getRuntime().availableProcessors() + 1;
        return availableProcessors > max ? max : availableProcessors;
    }
}
