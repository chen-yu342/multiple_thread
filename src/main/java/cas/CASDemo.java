package cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS比较并交换 compareAndSet
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        //main do thing.....
        System.out.println(atomicInteger.compareAndSet(5, 2024) +", current data: "+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2024) +", current data: "+atomicInteger.get());
        //TODO 原理: CAS的执行靠的是底层的unsafe类来保证原子性，Unsafe类大多是 rt.jar里面的，原生的，Unsafe包里面的类，native修饰，
        // 这是底层额类，这是一个后门，C指针直接曹总内存，
        // 是系统原语，很多的指令组成，不允许打断，CPU的原子指令，不会造成数据不一致的问题
        // CAS 的缺点，最大的问题就是ABA问题？如何规避ABA问题呢？ABA:狸猫换太子，A、B两个线程，A需要10s，B 2s完成....B把value=1改为2，
        // 最后后又改为了1，此时A进来，一看值是原来期待的1，但是其实不是了
    }
}
