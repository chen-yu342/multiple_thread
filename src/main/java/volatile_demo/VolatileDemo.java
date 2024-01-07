package volatile_demo;

import java.util.concurrent.TimeUnit;

class MyData {
    volatile int  number = 0;

    public void addT060() {
        this.number = 60;
    }
    public void addPlusPlus() {
        number++;
    }
}

/**
 * 1.验证volatile的可见性
 * 1.1 假如 int number = 0;number之前根本没有添加volatile关键字修饰
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
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

        //TODO 1.2验证不保证原子性
        for (int i = 0; i < 20; i++) {
                new Thread(()->{
                    for (int j = 0; j < 1000; j++) {
                        myData.addPlusPlus();
                    }

                },String.valueOf(i)).start();
        }
        //main 等到上面的线程全部计算完,后台默认有两个线程，一个是main线程，一个是GC线程
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() +"finally value: "+myData.number);
    }
}
