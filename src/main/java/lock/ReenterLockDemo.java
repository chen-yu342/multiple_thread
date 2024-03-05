package lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockDemo  {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendMS();
        }, "t1").start();

        new Thread(() -> {
            phone.sendMS();
        }, "t2").start();
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        System.out.println();
        System.out.println();
        System.out.println();
        t3.start();
        t4.start();
    }

}

class Phone implements Runnable{
    public synchronized void sendMS() {
        System.out.println(Thread.currentThread().getName() + " Send MS");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + " Send Email");
    }

    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoke get ");
            set();
        }finally {
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoke set ");
        }finally {
            lock.unlock();
        }
    }
}