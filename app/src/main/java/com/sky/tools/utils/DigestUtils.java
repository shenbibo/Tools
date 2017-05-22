package com.sky.tools.utils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.in;

/**
 * DigestUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-03-20
 */
public class DigestUtils {

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    private DigestUtils() {
        throw new AssertionError();
    }

    /**
     * 获取文件夹中文件的MD5值
     *
     * @param file
     * @param listChild true递归子目录中的文件
     * @return
     */
    public static Map<String, String> getDirMD5(File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        String md5;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild) {
                Map<String, String> tempMap = getDirMD5(f, true);
                if (tempMap != null) {
                    map.putAll(tempMap);
                }
            } else {
                md5 = getFileMD5(f);
                if (md5 != null) {
                    map.put(f.getAbsolutePath(), md5);
                }
            }
        }
        return map;
    }

    /**
     * 读取文件的MD5值
     * */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        BufferedInputStream bis = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            bis = new BufferedInputStream(new FileInputStream(file), 8192 * 4);
            while ((len = bis.read(buffer, 0, 8192)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            IoUtils.close(bis);
        }
        return bytesToHexString(digest.digest());
    }

    /**
     *
     * 获取String的MD5值
     * @param str
     * @return String
     */
    public static String getStringMd5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            return null;
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
