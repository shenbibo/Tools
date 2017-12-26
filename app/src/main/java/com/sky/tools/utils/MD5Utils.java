package com.sky.tools.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 一句话注释。
 * <p>
 * 详细内容。
 *
 * @author sky on 2017/12/26
 */

public class MD5Utils {
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取MD5值字符串
     */
    public static String getMD5HexStr(String key, String charsetName) {
        String md5Str = null;
        if (key == null) {
            return md5Str;
        }
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(charsetName == null ? key.getBytes() : key.getBytes(charsetName));
            md5Str = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5Str;
    }
}
