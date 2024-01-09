package volatile_demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    volatile int  number = 0;

    public void addT060() {
        this.number = 60;
    }
    public  void addPlusPlus() {
        number++;
    }
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}


public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();

         //TODO 1.验证volatile的可见性
        //  1.1 假如 int number = 0;number之前根本没有添加volatile关键字修饰

        /*new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t com in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addT060();
            System.out.println(Thread.currentThread().getName() + "\t update number value:" + myData.number);
        }, "AAA").start();
        while (myData.number == 0) {
        //main 线程一直等待，直到number不等于0，只要跳出了这个循环，那么就是感知到了number的值
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over");*/
        //TODO  结果是main线程是一直等待，没有人来通知main，别人的操作自己不可见，这个程序一直存在,如果在number加上volatile那么main可见

        //TODO 1.2验证不保证原子性，原子性：
        // 不可分割，完整性，也即是某个线程正在做某个具体的业务时，中间不可以被加塞或者被分割，要么同时成功，要么同时失败
        for (int i = 0; i < 20; i++) {
                new Thread(()->{
                    for (int j = 0; j < 1000; j++) {
                        myData.addPlusPlus();
                        myData.addAtomic();
                    }

                },String.valueOf(i)).start();
        }
        //TODO main 等到上面的线程全部计算完,后台默认有两个线程，一个是main线程，一个是后台GC线程
        while (Thread.activeCount()>2){
            Thread.yield();//yield礼让线程,不执行，让给别人来执行
        }
        //todo 最后发现结果的值一直小于20000，因为不保证原子性，想得到20000再  public synchronized void addPlusPlus() {
        //        number++;
        //    } 但是这样杀鸡用牛刀，只是得到了一个number++，不需要用synchronized
        System.out.println(Thread.currentThread().getName() +" number finally value: "+myData.number);
        //TODO 如何解决原子性 2.1 sync 2.2 atomic
        System.out.println(Thread.currentThread().getName() +" atomicInteger finally value: "+myData.atomicInteger);
        // todo atomicInteger为什么可以解决线程问题，因为CAS->Unsafe类
    }
}
