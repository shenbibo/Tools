package com.sky.tools.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * ResUtils
 *
 */
public class ResUtils {

    private ResUtils() {
        throw new AssertionError();
    }

    /**
     * get content from a raw resource. This can only be used with resources whose value is the name of an asset files
     * -- that is, it can be used to open drawable, sound, and raw resources; it will fail on string and color
     * resources.
     * 
     * @param context
     * @param resId The resource identifier to open, as generated by the appt tool.
     * @return
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        BufferedReader br = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            IoUtils.close(br);
        }
    }

    /**
     * same to {@link ResUtils#geFileFromRaw(Context, int)}, but return type is List<String>
     * 
     * @param context
     * @param resId
     * @return
     */
    public static List<String> geFileToListFromRaw(Context context, int resId) {
        if(context == null){
            return null;
        }

        List<String> fileContent = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
            reader = new BufferedReader(in);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            IoUtils.close(reader);
        }
    }
}
