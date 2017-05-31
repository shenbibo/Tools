package com.sky.tools.utils;

import android.support.test.runner.AndroidJUnit4;

import com.sky.tools.log.LogcatTree;
import com.sky.tools.log.Setting;
import com.sky.tools.log.Slog;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
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
    public static void init() {
        Slog.init(new LogcatTree()).showThreadInfo(true).prefixTag("sky.test.tools");
    }

    @Test
    public void logIWithDefaultTag() {
        Slog.i("i no prefixTag Test");
        Slog.i("i no prefixTag test = %d", 2);
    }

    @Test
    public void logEWithCustomTag() {
        Slog.t("custom").e("i prefixTag Test");
        Slog.t("custom2").e("i prefixTag test = %d", 2);
        Slog.t("custom3").e(new Throwable(), "i prefixTag test = %d", 3);
    }

    @Test
    public void logWithSwitchSimpleMode() {
        Slog.s(true).i("testSimpleMode");
    }

    @Test
    public void logWithMultiMethodCount() {
        Slog.m(3).i("test three method count println");
        Slog.m(0).i("test 0 method count print, so hide track");
    }

    @Test
    public void logWithHideThreadInfo() {
        Slog.th(false).i("hide thread info");
    }

    @Test
    public void logWithMethodHideInfoSimpleModeTag() {
        Slog.s(true).m(100).th(true).i("s(true).m(100).th(true)");
        Slog.s(false).m(100).th(false).i("set s(false).m(100).th(false)");
        Slog.s(false).m(0).th(true).t("s(false).m(0).th(true)").i("s(false).m(0).th(true).tag");
    }

    @Test
    public void logWithMethodOffset(){
        Slog.o(1).i("1 offset");
        Slog.o(2).i("2 offset");
        Slog.o(100).i("100 offset");
    }

    //    @Test
    public void logCollectTest() {
        logWithSwitchSimpleMode();
        logWithMultiMethodCount();
        logWithHideThreadInfo();
        logWithMethodHideInfoSimpleModeTag();
    }

    @Test
    public void json() {}

    @Test
    public void xml() {}

    @Test
    public void setting() {
        Setting setting = Slog.getSetting();
        Slog.i(setting.toString());

        int count = 3;
        setting.methodCount(count);
        Slog.i("global methodCount set to %d", count);

        int priority = Slog.WARN;
        setting.logPriority(priority);
        Slog.i("global logPriority set to %d", priority);

        // reset to full
        setting.logPriority(Slog.FULL);

        int methodOffset = 1;
        setting.methodOffset(methodOffset);
        Slog.i("global methodOffset set to %d", methodOffset);

        boolean showThreadInfo = false;
        setting.showThreadInfo(showThreadInfo);
        Slog.i("global showThreadInfo set to %b", showThreadInfo);


        boolean simpleMode = true;
        setting.simpleMode(simpleMode);
        Slog.i("global simpleMode set to %b", simpleMode);

        String prefixTag  = "new test Tag";
        setting.prefixTag(prefixTag);
        Slog.i("global prefixTag set to %s", prefixTag);

        // after set all above param, now i user everytime setting
        Slog.t("good").m(5).o(2).s(false).th(true).i("this is the old story!!!");
    }

    @Test
    public void longLogTest(){
        StringBuilder sb = new StringBuilder((int) (8192 / 0.75));
        for (int i = 0; i < 8192; i++){
            sb.append(i);
        }
        Slog.t("longLogTest").i(sb.toString());
    }
    
    @Test
    public void multiLineLogTest(){
        StringBuilder sb = new StringBuilder((int) (8192 / 0.75));
        for (int i = 0; i < 8192; i++){
            if(i % 30 == 0){
                sb.append("\n");
            }
            sb.append(i);
        }
        Slog.t("multiLineLogTest").i(sb.toString());
    }

    @Test
    public void nullObject(){
        Slog.dO(null);
    }

    @Test
    public void nullAndEmptyStringTest(){
        Slog.i(null);
        Slog.i("");
    }

    @Test
    public void object() {
        String[] name = {"sga", "gsadgsa", "sgdsfhds"};
        Slog.i("array test");
        Slog.dO(name);

        String[] name2 = {"wwt", "wetgety", "reyertu"};
        Slog.i("list test");
        Slog.dO(Arrays.asList(name2));

        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < name.length; i++){
            map.put(name[i], name2[i]);
        }
        Slog.i("map test");
        Slog.dO(map);

        String[] name3 = {"wggsg", "hketydfhdsh", "7887reyertu"};
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(name3));
        Slog.i("set test");
        Slog.dO(set);

    }

    @Test
    public void logWithDefaultTagFormNoneToFull() {
        Slog.log(-100, "FormNoneToFull", null, "-100");             // 无打印
        Slog.log(Slog.NONE, "FormNoneToFull", null, "NONE1111");
        Slog.log(Slog.VERBOSE, "FormNoneToFull", null, "INFO");
        Slog.log(Slog.INFO, "FormNoneToFull", null, "INFO");
        Slog.log(Slog.WARN, "FormNoneToFull", null, "WARN");
        Slog.log(Slog.ERROR, "FormNoneToFull", null, "ERROR");
        Slog.log(Slog.ASSERT, "FormNoneToFull", null, "ASSERT");
        Slog.log(Slog.FULL, "FormNoneToFull", null, "info");        // 不打印
        Slog.log(100, "FormNoneToFull", null, "+100");              // 不答应
    }

    /**
     * 测试多线程设置tag的准确性
     */
    @Test
    public void multithreadingPrintLog() throws InterruptedException {
        countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new LogTestThread(i).start();
        }
        countDownLatch.await();
    }

    private class LogTestThread extends Thread {
        int index;

        LogTestThread(int i) {
            index = i;
            setName("Thread_" + i);
        }

        @Override
        public void run() {
            String tag = "LogTestThread_" + index;
            for (int i = 0; i < 20; i++) {
                Slog.t(tag).d(tag + "_" + i);
            }
            countDownLatch.countDown();
        }
    }
}
