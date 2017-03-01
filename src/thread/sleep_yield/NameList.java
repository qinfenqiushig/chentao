/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.sleep_yield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author chentao
 * @version $Id: NameList.java, v 0.1 2016年6月2日 上午9:48:38 chentao Exp $
 */
public class NameList {

    /**
     * 虽然集合是线程安全的，但是对集合的操作还是可以并发的，所以仍然是不安全的
     */
    private List<String> nameList = Collections.synchronizedList(new ArrayList<String>());

    public void addList(String str){
        nameList.add(str);
    }

    /**
     * sleep和yield方法，不会释放锁
     *
     * 若释放锁很大可能会报异常，
     * 不释放锁，sleep和yield只是让出cpu时间片，仍保持对象的锁，其他线程无法调用removeFirst方法
     *
     * @return
     */
    public synchronized String removeFirst(){
        if(nameList.size()>0){
            String first =  nameList.remove(0);
            System.out.println(first);
            Thread.yield();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            addList("world");
            return first;
        }else{
            throw new RuntimeException("集合为空");
        }
    }

    public static void main(String[] args) {
        NameList nl = new NameList();
        nl.addList("hello");
        NameDropable nd = nl.new NameDropable();
        Thread t1 = new Thread(nd);
        Thread t2 = new Thread(nd);
        t1.start();
        t2.start();
    }
    class NameDropable implements Runnable{
        @Override
        public void run(){
            removeFirst();
        }
    }
}

