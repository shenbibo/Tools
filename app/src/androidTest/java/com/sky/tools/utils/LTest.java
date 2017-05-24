package com.sky.tools.utils;

import android.support.test.runner.AndroidJUnit4;

import com.sky.tools.utils.log.L;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/24.
 */
@RunWith(AndroidJUnit4.class)
public class LTest {
    @BeforeClass
    public static void init(){
        L.init();
    }

    @Test
    public void LogIWithDefaultTag(){
        L.i("i no tag Test");
        L.i("i test = %d", 2);
        L.i(new Throwable(), "i test = %d", 3);
    }

    @Test
    public void LogIWithCustomTag(){
        L.i("CUSTOM", "i tag Test");
        L.i("CUSTOM", "i tag test = %d", 2);
        L.i("CUSTOM", new Throwable(), "i tag test = %d", 3);
    }
}
