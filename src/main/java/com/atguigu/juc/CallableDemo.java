package com.atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 我是你爹
 */
class MyThread implements Callable<String>{
    @Override
    public String call() throws Exception {
        System.out.println("---------come in");
        Thread.sleep(5000);
        return "hello 1116java";
    }
}
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask(()->{
            System.out.println("--------------come");
            return "hello 1116";
        });
        Thread thread = new Thread(futureTask,"A");
        thread.start();
        System.out.println(Thread.currentThread().getName()+"-----end");
        System.out.println(futureTask.get());
    }
}
