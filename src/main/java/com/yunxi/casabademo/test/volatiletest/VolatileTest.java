package com.yunxi.casabademo.test.volatiletest;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-03-29
 * @Description: Volatile测试
 */
public class VolatileTest {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) throws InterruptedException {
        final VolatileTest test = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++)
                    test.increase();
            }).start();
        }

        Thread.sleep(3000);
        System.out.println(test.inc);
    }
}
