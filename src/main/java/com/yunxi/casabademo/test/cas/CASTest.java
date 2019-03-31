package com.yunxi.casabademo.test.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-03-29
 * @Description: AtomicInteger测试
 */
public class CASTest {
    public AtomicInteger inc = new AtomicInteger(0);

    public void increase() {
        inc.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        final CASTest test = new CASTest();
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
