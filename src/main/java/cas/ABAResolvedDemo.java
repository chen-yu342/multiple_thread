package cas;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABAResolvedDemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        //TODO 以下是ABA问题的产生
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();

        new Thread(()->{
            //TODO 停1s 等上面完成一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(atomicReference.compareAndSet(100,2023)+"\t"+atomicReference.get());
        },"t2").start();

        System.out.println("以下是ABA问题的解决");
        //TODO 停2s 等上面完操作全部完成
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            //TODO 先拿到版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t"+atomicStampedReference.getReference()+"\tfirst stamp: " +stamp);
            //TODO sleep一下，等t4也拿到同样的版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+atomicStampedReference.getReference()+" second stamp: "+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t"+atomicStampedReference.getReference()+" 第三次 stamp: "+atomicStampedReference.getStamp());
        },"t3").start();

        new Thread(()->{
            //TODO 等t4拿到同样的版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() +"\t 第一次的版本号 "+stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 2023, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"\t修改成功否 "+result+" 当前最新的实际版本号 "+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+" 当前最新的实际value "+atomicStampedReference.getReference());
        },"t4").start();
        /*
        * t4	 第一次的版本号 1
          t3	100	first stamp: 1
          t3	101 second stamp: 2
          t3	100 第三次 stamp: 3
          t4	修改成功否 false 当前最新的实际版本号 3
          t4 当前最新的实际value 100
        * */
    }
}
