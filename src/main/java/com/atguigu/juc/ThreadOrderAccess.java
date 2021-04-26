package com.atguigu.juc;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我是你爹
 *
 */
class Test1{
    int fag = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    public void testList() throws InterruptedException {
        lock.lock();
        try{
            //判断
            while (fag != 1 && "A".equals(Thread.currentThread().getName())){
                condition1.await();
            }
            while (fag != 2 && "B".equals(Thread.currentThread().getName())){
                condition2.await();
            }
            while (fag != 3 && "C".equals(Thread.currentThread().getName())){
                condition3.await();
            }
            //干活
            for (int i = 1; i <= fag * 5; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
            //修改标志位
            if (fag == 3){
                fag=1;
            }else{
                fag++;
            }
            switch (fag){
                case 1:
                    condition1.signal();break;
                case 2:
                    condition2.signal();break;
                case 3:
                    condition3.signal();break;
            }
        }finally{
            lock.unlock();
        }
    }
    public void test5() throws InterruptedException {
        lock.lock();
        try{

            //判断
            while (fag != 1){
                condition1.await();
            }
            //干活
            for (int i = 1; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
            fag = 2;
            //通知
            condition2.signal();
        }finally{
            lock.unlock();
        }
    }
    public void test10() throws InterruptedException {
        lock.lock();
        try{
            while (fag != 2){
                condition2.await();
            }
            for (int i = 1; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
            fag=3;
            condition3.signal();
        }finally{
            lock.unlock();
        }
    }
    public void test15() throws InterruptedException {
        lock.lock();
        try{
            while (fag != 3){
                condition3.await();
            }
            for (int i = 1; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
            fag=1;
            condition1.signal();
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
                    test1.testList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    test1.testList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    test1.testList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
    }
}
