package thread.threadLocal;

public class TestThreadLocal {

    private  ThreadLocal<Long> threadLocal1 = new ThreadLocal<Long>();
    private  ThreadLocal<String> threadLocal2 = new ThreadLocal<String>();
    
    public Long getLong(){
        return threadLocal1.get();
    }
    
    public void setLong(Long i){
        threadLocal1.set(i);
    }
    
    public void setString(String str){
        threadLocal2.set(str);
    }
    
    public String getString(){
        return threadLocal2.get();
    }
    
    public static void main(String[] args) throws InterruptedException {
        TestThreadLocal threadLocal = new TestThreadLocal();
        Thread thread1 = new Thread(new MyThread(threadLocal));
        Thread thread2 = new Thread(new MyThread(threadLocal));
        thread1.start();
        thread1.join();
        thread2.start();
    }
    
}

class MyThread implements Runnable{

    private TestThreadLocal threadLocal;
    
    MyThread(TestThreadLocal threadLocal){
        this.threadLocal = threadLocal;
    }
    
    @Override
    public void run() {
        threadLocal.setLong(Thread.currentThread().getId());
        threadLocal.setString(Thread.currentThread().getName());
        System.out.println("[Thread Id : ]"+threadLocal.getLong());
        System.out.println("[Thread Name : ]"+threadLocal.getString());
    }
    
}
