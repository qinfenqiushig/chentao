/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author chentao
 * @version $Id: TestBarrier.java, v 0.1 2016年6月7日 上午10:54:54 chentao Exp $
 */
public class TestBarrier {

    private int percent;

    public synchronized void download(){
        System.out.println("图片已下载"+(++percent)+"%... ...");
    }

    public static void main(String[] args) {
        TestBarrier tb = new TestBarrier();
        //创建障碍器，并设置MainTask为所有定数量的线程都达到障碍点时候所要执行的任务(Runnable)
        CyclicBarrier barrier = new CyclicBarrier(100, tb.new MainTask());

        for(int i=0;i<100;i++){
            new Thread(tb.new SubTask(barrier)).start();;
        }
    }
    class MainTask extends Thread{
        public void run(){
            System.out.println("----图片下载完成！----");
        }
    }

    class SubTask implements Runnable{
        private CyclicBarrier cb;

        SubTask(CyclicBarrier cb){
            this.cb = cb;
        }
        public void run(){
            download();
            try {
                //通知障碍器已经完成！
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
