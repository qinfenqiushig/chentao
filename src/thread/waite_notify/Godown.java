/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.waite_notify;

import java.io.IOException;
import java.util.Properties;

/**
 *  生产者和消费者模型
 * @author chentao
 * @version $Id: Godown.java, v 0.1 2016年6月3日 上午10:19:26 chentao Exp $
 */
public class Godown {

    private static final int MAX_SIZE = 100;
    private int curnum;
    private static final String NOPRODUCE_FORMAT = "+++要生产的产品数量：%d,大于当前剩余库存量：%d,暂时不能执行生产任务！";
    private static final String PRODUCE_FORMAT = "+++已生产产品：%d,,剩余产品数量为：%d,当前可生产量为：%d";

    private static final String NOCONSUME_FORMAT = "---当前产品余量为：%d,不足消费：%d,暂时不能执行消费任务！";
    private static final String CONSUME_FORMAT = "---已消费产品：%d,还剩产品%d,当前可生产量为：%d";

    Godown(int curnum){
        this.curnum = curnum;
    }

    public synchronized void produce(int neednum){
        while(neednum+curnum>MAX_SIZE){
            System.out.println(String.format(NOPRODUCE_FORMAT, neednum,MAX_SIZE-curnum));
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        curnum+=neednum;
        System.out.println(String.format(PRODUCE_FORMAT, neednum,curnum,MAX_SIZE-curnum));
        //解除所有消费的等待
        notifyAll();
    }

    public synchronized void consume(int neednum){
        while(curnum<neednum){
            System.out.println(String.format(NOCONSUME_FORMAT, curnum,neednum));
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        curnum-=neednum;
        System.out.println(String.format(CONSUME_FORMAT, neednum,curnum,MAX_SIZE-curnum));
        //解析所有生产的等待
        notifyAll();
    }

    class Producer implements Runnable{

        private int neednum;
        Producer(int neednum){
            this.neednum = neednum;
        }
        public void run(){
            produce(neednum);
        }
    }

    class Consumer implements Runnable{
        private int neednum;
        Consumer(int neednum){
            this.neednum = neednum;
        }

        public void run(){
            consume(neednum);
        }
    }

    public static void main(String[] args) throws IOException {
        Godown gd = new Godown(50);
        for(int i=100;i>0;i-=10){
            new Thread(gd.new Consumer(i)).start();
        }
        for(int i=10;i<100;i+=10){
            new Thread(gd.new Producer(10)).start();
        }

    }
}
