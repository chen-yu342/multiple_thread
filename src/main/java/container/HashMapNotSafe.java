package container;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class HashMapNotSafe {

    public static void main(String[] args) {
        //TODO HashMap 线程不安全code,会出现并发修改异常 java.util.ConcurrentModificationException
        //HashMap<String,String> map = new HashMap<>();
        //TODO 解决 ConcurrentHashMap
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
        //TODO 考点HashMap是key value键值对，HashSet是单一的值，为什么说HashSet的底层是hashMap？答案:的确是！
        // private static final Object PRESENT = new Object();
        // public boolean add(E e) {
        //        return map.put(e, PRESENT)==null;
        //    }其实 HashSet不关心value,只关系key，value是一个常量

    }
}
