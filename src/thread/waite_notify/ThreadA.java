/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.waite_notify;

/**
 *
 * @author chentao
 * @version $Id: ThreadA.java, v 0.1 2016年5月27日 上午10:14:47 chentao Exp $
 */
public class ThreadA {

    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        b.start();
        //线程A拥有b对象上的锁。线程为了调用wait()或notify()方法，该线程必须是那个对象锁的拥有者
        synchronized (b) {
            System.out.println("等待对象b完成计算... ...");
            try {
                b.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("计算结果为："+b.getCount());
        }
    }
}

class ThreadB extends Thread{
    private int count = 0;
    @Override
    public void run(){
        synchronized(this){
            for(int i=1;i<=100;i++){
                count+=i;
                System.out.println(count);
            }
            //（完成计算了）唤醒在此对象监视器上等待的单个线程，在本例中线程A被唤醒
            notify();
        }
    }
    public int getCount(){
        return count;
    }
}
