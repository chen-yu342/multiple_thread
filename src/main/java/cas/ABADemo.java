package cas;

import java.util.concurrent.atomic.AtomicReference;

class User{
    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    String  username;
    int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
public class ABADemo {
    public static void main(String[] args) {
        //TODO 这里是原子引用
        User zs = new User("zs", 23);
        User li4 = new User("li4", 29);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zs);
        System.out.println(atomicReference.compareAndSet(zs,li4)+"\t"+atomicReference.get());
        System.out.println(atomicReference.compareAndSet(zs,li4)+"\t"+atomicReference.get());
        //TODO true	User{username='li4', age=29}
        //    false	User{username='li4', age=29}
        //TODO 解决ABA问题 ： 新增一种机制，那就是修改版本号（类似时间戳） 不仅是比较值，还要比较版本号 JUC atomicStampedReference class


    }
}
