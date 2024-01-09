package volatile_demo;

public class SingleDemo {
    private SingleDemo() {
        System.out.println(Thread.currentThread().getName()+" 我是构造方法");
    }
    private static volatile SingleDemo instance = null;

    public  static  SingleDemo getInstance(){
        if (instance == null){
            instance = new SingleDemo();
        }
        return instance;
    }
    public  static  SingleDemo getInstanceDCL(){
        if (instance == null){
            synchronized (SingleDemo.class){
                if (instance == null){
                    instance = new SingleDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程 TODO 发现只会有一个实例
        /*System.out.println(SingleDemo.getInstance()==SingleDemo.getInstance());
        System.out.println(SingleDemo.getInstance()==SingleDemo.getInstance());
        System.out.println(SingleDemo.getInstance()==SingleDemo.getInstance());*/
        //TODO 多线程 违背了单例模式， 怎么解决；1.加synchronized； public  static synchronized SingleDemo getInstance(){} 可以解决，
        // 但是太重了，直接锁住了整段代码，我们只想锁住 instance = new SingleDemo(); 2.DCL 双端检索机制
        System.out.println("================");
        /*for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingleDemo.getInstance();
            },String.valueOf(i)).start();
        }*/
        //TODO  DCL
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingleDemo.getInstanceDCL();
            },String.valueOf(i)).start();
        }
        //TODO getInstanceDCL()但是机制不一定安全，几千万次可能会发生一两次，原因是指令重排,需要加volatile 禁止指令重排，
        // new 对象，分3步，1.分配对象的内存空间 2.初始化对象 3.设置instance指向刚刚分配的地址，此时instance!=null，
        // 由于指令重排 2 3颠倒，所以来获取的时候判断不为空，就返回了，但其实没有拿到真实的值，
        // 会发生线程安全问题,所以需要在 private static volatile SingleDemo instance = null; DCL + Volatile解决
        // volatile满足JMM的两个条件，不满足原子性,所以是轻量级（乞丐版）的synchronized
    }
}
