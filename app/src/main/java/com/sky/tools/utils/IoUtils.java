package com.sky.tools.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO utils
 *
 * @author Vladislav Bauer
 */

public class IoUtils {

    private IoUtils() {
        throw new AssertionError();
    }


    /**
     * Close closable object and catch {@link Exception}
     * @param closeable closeable object
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
