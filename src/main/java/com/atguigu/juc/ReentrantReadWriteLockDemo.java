package com.atguigu.juc;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 我是你爹
 */
class MyCaChe{

    Map<String,String> map = new ConcurrentHashMap<>();
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public void write(String key,String value)
    {
        readWriteLock.writeLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---正在写入");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t"+"---完成写入");
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
    public void read(String key)
    {
        readWriteLock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---正在读取");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t"+"---完成读取 "+result);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

}
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyCaChe myCaChe = new MyCaChe();
        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCaChe.write(finalI +"", finalI +"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myCaChe.read(finalI +"");
            },String.valueOf(i)).start();
        }

    }

    public static void thread(MyCaChe myCaChe) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 11, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            try{
                threadPoolExecutor.execute(()->{
                    for (int j = 0; j < 10; j++) {
                        int finalI = j;
                        myCaChe.write(finalI +"", finalI +"");
                    }
                });
            }finally{

            }
        }
        for (int i = 0; i < 10; i++) {
            try{
                threadPoolExecutor.execute(() -> {
                    for (int j = 0; j < 10; j++) {
                        int finalI = j;
                        myCaChe.read(finalI + "");
                    }
                });
            }finally{
                threadPoolExecutor.shutdown();
            }
        }
    }
}
