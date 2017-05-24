package com.sky.tools.utils;

import android.support.test.runner.AndroidJUnit4;

import com.sky.tools.utils.log.Slog;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/24.
 */
@RunWith(AndroidJUnit4.class)
public class SlogTest {
    @BeforeClass
    public static void init(){
        Slog.init();
    }

    @Test
    public void logIWithDefaultTag(){
        Slog.i("i no tag Test");
        Slog.i("i no tag test = %d", 2);
        Slog.i(new Throwable(), "i no tag test = %d", 3);
    }

    @Test
    public void logEWithCustomTag(){
        Slog.t("custom").e("i tag Test");
        Slog.t("custom2").e("i tag test = %d", 2);
        Slog.t("custom3").e(new Throwable(), "i tag test = %d", 3);
    }

    @Test
    public void logWithDefaultTag(){
        Slog.log(-100, "-100");             // 无打印
        Slog.log(Slog.NONE, "NONE1111");    //
        Slog.log(Slog.INFO, "INFO");
        Slog.log(Slog.WARN, "WARN");
        Slog.log(Slog.ERROR, "ERROR");
        Slog.log(Slog.ASSERT, "ASSERT");
        Slog.log(Slog.FULL, "info");        // 不打印
        Slog.log(100, "+100");              // 不答应
    }
}
