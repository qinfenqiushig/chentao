/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.join;

/**
 *
 * @author chentao
 * @version $Id: TestJoin.java, v 0.1 2016年6月29日 下午4:04:17 chentao Exp $
 */
public class TestJoin {

    public static void main(String[] args) {
        AThread a = new AThread();
        BThread b = new BThread(a);
        a.start();
        b.start();
        try {
            //bThread运行结束后，主线程才会继续向下运行
            b.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread end.");
    }
}

class AThread extends Thread {

    public AThread() {
        super("[AThread] Thread");
    }
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+" start.");
        for(int i=0;i<5;i++){
            System.out.println(threadName+" loop at "+i);
        }
        System.out.println(threadName+" end.");
    }

}

class BThread extends Thread {

    private Thread thread;
    /**
     *
     */
    public BThread(Thread thread) {
        super("[BThread] Thread");
        this.thread = thread;
    }

    /**
     *
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+" start.");
        try {
            //aThread运行结束后，bThread才会继续向下运行
            thread.join();
            for(int i=0;i<5;i++){
                System.out.println(threadName+" loop at "+i);
            }
            System.out.println(threadName+" end.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}