import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 我是你爹
 * 实现故障ArrayList
 * 发现故障：java.util.ConcurrentModificationException
 * 导致原因：ArrayList线程不安全
 * 解决方案：3.1 newVector<>()
 *          3.2 Collections.synchronizedList(new ArrayList<>());使用SSS
 *          3.3写时复刻技术，CopyOnWriteArrayList
 * 优化建议：(同样的错误，无第二次)
 * 读多少写多少，有用CopyOnWriteArrayList类解决线程安全问题
 */
public class NotSafeDemo {
    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,6));
                System.out.println("map = " + map);
            },String.valueOf(i)).start();
        }

    }

    public static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {set.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println("set = " + set);
            },String.valueOf(i)).start();
        }
    }

    public static void listNotSafe() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {list.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println("list = " + list);
            },String.valueOf(i)).start();
        }
    }
}
