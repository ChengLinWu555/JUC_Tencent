package com.atguigu.juc;

import java.sql.Connection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我是你爹
 *
 */
class Test1{
    int fag = 1;
    int number = 5;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    public void testList(int fag,int number) throws InterruptedException {
        lock.lock();
        try{
            //判断
//            switch (fag){
//                case 1:condition3.await();condition2.signal();fag = 2;
//                case 2:condition1.await();condition3.signal();fag = 3;number = 10;
//                case 3:condition2.await();condition1.signal();fag = 1;number = 15;
//            }
            //干活
            //判断
            while (fag !=1){
                condition1.await();
            }
        }finally{
            lock.unlock();
        }

    }
}
public class ThreadOrderAccess {
    public static void main(String[] args) {
        Test1 test1 = new Test1();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    test1.testList(1,5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    test1.testList(2,10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    test1.testList(3,15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
    }
}
