package com.sky.tools.utils;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.sky.tools.application.MainApplication;
import com.sky.tools.main.MainActivity;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/19.
 */
@RunWith(AndroidJUnit4.class)
public class SpUtilsTest {
    private static final String TAG = "SpUtilsTest";

//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
//            MainActivity.class);

    @BeforeClass
    public static void init() {
        SpUtils.init(MainApplication.getApplication(), "test");
    }


    @Test
    public void putAndGetStringArray() {
        String[] testArray = {"123", "sjgks", "sky", "gavin", "smith", "456"};
        SpUtils.putStringArray("string_array_test", testArray);
        String[] getTestArray = SpUtils.getStringArray("string_array_test", null);
        for (int i = 0; i < testArray.length; i++) {
            Assert.assertEquals(testArray[i], getTestArray[i]);
        }
    }
}
