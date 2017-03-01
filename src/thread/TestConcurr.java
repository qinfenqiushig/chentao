/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TestConcurr {
    
    static final HashMap<String,String> map = new HashMap<String,String>();
    static final ConcurrentHashMap<String,String> conMap = new ConcurrentHashMap<String,String>();
    private static long value = 0l;
    private volatile boolean done;
    
    public void work(){
        while(!done){
            System.out.println(value++);
        }
    }
    public void setDone(boolean done){
        this.done = done;
    }
    public synchronized static void inc(){
        System.out.println(value++);
    }
    
    public static void main(String[] args) throws InterruptedException {
//        final TestConcurr tc = new TestConcurr();
//        Thread t1 = new Thread(new Runnable(){
//            @Override
//            public void run() {
//                tc.work();
//            }
//        });
//        RealMan rm = tc.new RealMan();
//        Thread t = new Thread(rm);
//        t1.start();
//        t.start();
        for(int i=0;i<10;i++){
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
//                    TestConcurr.inc();
                    System.out.println(Thread.currentThread().getName()+value++);
                }
            });
            t.start();
        }
    }

    class RealMan implements Runnable{

        /** 
         * 
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            TestConcurr.this.setDone(true);
        }
        
    }
}
