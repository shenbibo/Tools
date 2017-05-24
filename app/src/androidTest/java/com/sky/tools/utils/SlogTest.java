package com.sky.tools.utils;

import android.support.test.runner.AndroidJUnit4;

import com.sky.tools.log.Slog;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * [function]
 * [detail]
 * Created by Sky on 2017/5/24.
 */
@RunWith(AndroidJUnit4.class)
public class SlogTest {
    private static CountDownLatch countDownLatch;
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

    /**
     * 测试多线程设置tag的准确性
     * */
    @Test
    public void multithreadingPrintLog() throws InterruptedException {
        countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++){
            new LogTestThread(i).start();
        }
        countDownLatch.await();
    }

    private class LogTestThread extends Thread{
        int index;
        LogTestThread(int i){
            index = i;
        }

        @Override
        public void run() {
            String tag = "LogTestThread_" + index;
            for (int i = 0; i < 1000; i++){
                Slog.t(tag).d(tag);
            }
            countDownLatch.countDown();
        }
    }
}
