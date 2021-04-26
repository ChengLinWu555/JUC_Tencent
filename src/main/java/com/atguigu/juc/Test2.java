package com.atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我是你爹
 */
public class Test2 {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();
        new Thread(() -> {synchronized (objectA){
            System.out.println(Thread.currentThread().getName()+"已经获得A锁，希望获得B锁");
            synchronized (objectB){
                System.out.println(Thread.currentThread().getName()+"成功获得B锁");
            }
        }},"A").start();
        new Thread(() -> {synchronized (objectB){
            System.out.println(Thread.currentThread().getName()+"已经获得B锁，希望获得A锁");
            synchronized (objectA){
                System.out.println(Thread.currentThread().getName()+"成功获得A锁");
            }
        }},"B").start();
    }
}
