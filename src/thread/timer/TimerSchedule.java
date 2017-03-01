/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSchedule {

    public static void main(String[] args) {
        Boolean roll = false;
        Timer timer = new Timer();
        timer.schedule(new MyTimeSchedule(roll),10000);
        int i=0;
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(++i);
        }
    }
    
}
class MyTimeSchedule extends TimerTask{
    private Boolean roll;
    public MyTimeSchedule(Boolean roll) {
        this.roll = roll;
    }
    /** 
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run() {
        System.out.println("time to get up!!!");
        roll = roll?false:true;
        Timer timer = new Timer();
        timer.schedule(new MyTimeSchedule(roll), roll?2000:4000);
    }
    
}
