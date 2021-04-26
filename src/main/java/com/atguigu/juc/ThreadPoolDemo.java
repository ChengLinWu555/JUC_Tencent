package com.atguigu.juc;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我是你爹
 */
class Demo{
    int number = 30;
    private Lock lock = new ReentrantLock();
    public void demo1(){
        lock.lock();
        try{
            if(number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" +(number--)+ "张还剩" + number);
            }
        }finally{
        lock.unlock();
        }
    }
}
public class ThreadPoolDemo {
    public static void main(String[] args) {
        Demo demo = new Demo();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());
        try{
            for (int i = 1; i <= 3; i++) {
                Future<String> submit = threadPoolExecutor.submit(() -> {
                    for (int j = 1; j <= 30; j++) {
                        demo.demo1();
                    }
                    return "666666";
                });
                System.out.println(submit.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally{
            threadPoolExecutor.shutdown();
        }

    }
}
