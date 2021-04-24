package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我是你爹
 * 1.高内聚低耦合的前提下，线程 、 操作 、 资源类
 * 2.判断、干活、通知
 * 3.防止虚假唤醒 使用while
 *
 */
class Test{
    int number = 0;
    public Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    //判断、干活、通知
    public void test1() throws InterruptedException {
        lock.lock();
        try{
            while (number != 0 ){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+number);
            condition.signal();
        }finally{
        lock.unlock();
        }

    }
    public void test2() throws InterruptedException {
        lock.lock();
        try{
            while (number == 0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+number);
            condition.signal();

        }finally{
            lock.unlock();
        }

    }
}
public class ConsumerDemo {
    public static void main(String[] args) {
        Test test = new Test();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    test.test1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    test.test2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
    }
}
