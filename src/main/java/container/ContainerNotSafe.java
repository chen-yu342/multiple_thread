package container;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContainerNotSafe {
    public static void main(String[] args) {
        //TODO 集合ArrayList<String>线程不安全的代码
        //TODO 出现java.util.ConcurrentModificationException，并发修改异常
        ArrayList<String> list = new ArrayList<>();
        // List<String> list = new Vector<String>();
        //List<String> list = Collections.synchronizedList(new ArrayList<String>());
        //CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
        /*
         * 1.故障现象
         * 出现java.util.ConcurrentModificationException，并发修改异常,
         * 2.导致原因
         *   TODO 并发争抢修改导致，参考花名册签名，一个同学在签名，还没有结束，写了一个张字，另外一个同学很暴力的把笔抢了过来，在纸上长长的划了一道
         * 3.解决方案
         *   1.Vector TODO 但是Vector是JDK1.0有的，ArrayList是JDK 1.2有的，比Vector晚,线程安全用Vector,要并发效率就用Arraylist
         *   2.Collections    TODO 用Collections工具类
         *   3.TODO CopyOnWrite写时复制,读写分离      public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
         * 4.优化建议
         * */
    }
}
