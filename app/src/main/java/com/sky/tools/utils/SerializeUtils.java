package com.sky.tools.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serialize Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-14
 */
public class SerializeUtils {

    private SerializeUtils() {
        throw new AssertionError();
    }

    /**
     * 反序列化，从文件恢复为对象
     *
     * @param filePath file path
     * @return de-serialized object
     */
    public static Object deserialization(String filePath) {
        ObjectInputStream in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(new FileInputStream(filePath));
            o = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(in);
            return o;
        }
    }

    /**
     * Serialize object to file.
     *
     * @param filePath file path
     * @param obj      object
     */
    public static void serialization(String filePath, Object obj) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(out);
        }
    }

}
