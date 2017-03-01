/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.waite_notify;

/**
 *  子线程运行2次，主线程运行4次，如此反复10次。
 *
 *  用到线程安全以及线程间的通信。
 *
 *  线程安全以及wait和notify，都是在资源类里面做的，而不是在线程里做，
 *  这样，在多个线程调用该对象时，不用再考虑线程安全问题。
 *
 * @author chentao
 * @version $Id: TurnRoll.java, v 0.1 2016年6月30日 上午10:55:05 chentao Exp $
 */
public class TurnRoll {

    public static void main(String[] args) {
        final Business bs = new Business();
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                for(int j=0;j<10;j++){
                    bs.sub(j);
                }
            }

        });
        thread.start();

        for(int j=0;j<10;j++){
            bs.main(j);
        }
    }
}

class Business {

    private boolean isOk = false;

    public synchronized void sub(int j){
        //这里用while不用if，是为了防止被意外notify或假notify情况，更加保险
        while(isOk){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<2;i++){
            System.out.println("sub loop at "+i+" of "+j);
        }
        isOk = true;
        notify();
    }

    public synchronized void main(int j){
        while(!isOk){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<4;i++){
            System.out.println("main loop at "+i+" of "+j);
        }
        isOk = false;
        notify();
    }
}