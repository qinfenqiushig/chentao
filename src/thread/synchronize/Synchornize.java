/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.synchronize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Synchornize {

    public static void main(String[] args) {
        String name = "chentaozhianyezh";
        Lock lock = new ReentrantLock();
        Thread thread = new Thread(new Output(name,lock));
        Thread thread1 = new Thread(new Output(name,lock));
        thread.start();
        thread1.start();
    }
    
}

class Output implements Runnable{
    private String name;
    private Lock lock;
    public Output(String name,Lock lock) {
        this.name = name;
        this.lock = lock;
    }
    @Override
    public void run() {
        char[] letters = name.toCharArray();
        while(true){
            lock.lock();
            for(char c:letters){
                System.out.print(c);
            }
            System.out.println();
            lock.unlock();
        }
    }
}
