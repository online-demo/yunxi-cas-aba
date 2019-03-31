package com.yunxi.casabademo.test.aba;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-03-29
 * @Description: AtomicStampedReference测试
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AbaTest {

    private static AtomicInteger atomicInteger = new AtomicInteger(100);

    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<Integer>(100, 0);

    public static void main(String[] args) throws InterruptedException {

        /**
         * 模拟发生ABA问题
         */
        Thread intT1 = new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            atomicInteger.compareAndSet(101, 100);
        });

        Thread intT2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {

            }
            boolean atomicIntegerResult = atomicInteger.compareAndSet(100, 101);

            System.out.println("AtomicInteger execute result = " + atomicIntegerResult);
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();


        Thread refThread1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("refThread1 atomicStampedReference.getStamp() = " + atomicStampedReference.getStamp());
        });

        Thread refThread2 = new Thread(() -> {
            // stamp发生变化
            int stamp = atomicStampedReference.getStamp();
            System.out.println("refThread2 atomicStampedReference.getStamp() = " + atomicStampedReference.getStamp());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            boolean atomicStampedReferenceResult = atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.out.println("AtomicStampedReference execute result = " + atomicStampedReferenceResult);
        });

        refThread1.start();
        refThread2.start();
    }
}
