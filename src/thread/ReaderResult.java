/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread;

/**
 *
 * @author chentao
 * @version $Id: ReaderResult.java, v 0.1 2016年5月27日 下午4:44:29 chentao Exp $
 */
public class ReaderResult extends Thread{

    private Calculator c;
    ReaderResult(Calculator c){
        this.c = c;
    }
    @Override
    public void run(){
        synchronized(c){
            try {
                System.out.println("等待计算结果... ...");
                c.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread()+"计算结果为:"+c.getCount());
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        new ReaderResult(calculator).start();
        new ReaderResult(calculator).start();
        new ReaderResult(calculator).start();
        calculator.start();
    }
}

class Calculator extends Thread{
    private int count=0;
    @Override
    public void run(){
        synchronized(this){
            for(int i=1;i<=100;i++){
                count+=i;
            }
        }
        //如果计算线程已经调用了notifyAll()方法，那么它就不会再次调用notifyAll()，----并且等待的读取线程将永远保持等待。
        notifyAll();
    }

    public int getCount(){
        return count;
    }
}
