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
        Slog.t("custom").i("i tag Test");
        Slog.t("custom2").i("i tag test = %d", 2);
        Slog.t("custom").i(new Throwable(), "i tag test = %d", 3);
    }

    @Test
    public void logWithDefaultTag(){
        Slog.log(-100, "info");
        Slog.log(Slog.NONE, "info");
        Slog.log(Slog.INFO, "info");
        Slog.log(Slog.WARN, "info");
        Slog.log(Slog.ERROR, "info");
        Slog.log(Slog.ASSERT, "info");
        Slog.log(Slog.FULL, "info");
        Slog.log(100, "info");
    }
}
