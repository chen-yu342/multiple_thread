package container;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class HashSetNotSafe {

    public static void main(String[] args) {
        //TODO HashSet 线程不安全code,会出现并发修改异常 java.util.ConcurrentModificationException
        //HashSet<String> set = new HashSet<>();
        //解决 1.
        //Set<Object> set = Collections.synchronizedSet(new HashSet<>());
        // 解决 2.
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
        //TODO 考点HashMap是key value键值对，HashSet是单一的值，为什么说HashSet的底层是hashMap？答案:的确是！
        // private static final Object PRESENT = new Object();
        // public boolean add(E e) {
        //        return map.put(e, PRESENT)==null;
        //    }其实 HashSet不关心value,只关系key，value是一个常量

    }
}
