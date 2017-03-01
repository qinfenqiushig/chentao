/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread;

/**
 *
 * @author chentao
 * @version $Id: Test.java, v 0.1 2016年5月30日 下午3:23:59 chentao Exp $
 */
public class Test {

    public static void main(String[] args) {
        Thread t1 = new MyThread();
        Thread t2 = new Thread(new MyRunnable());
        t1.start();
        t2.start();
    }
}

class MyThread extends Thread{
    @Override
    public void run(){
        for(int i=1;i<11;i++){
            System.out.println("线程1第"+i+"次执行...");
            Thread.yield();
//             try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}

class MyRunnable implements Runnable{
    @Override
    public void run(){
        for(int i=1;i<11;i++){
            System.out.println("线程2第"+i+"次执行...");
            Thread.yield();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}