
package com.seshu.threads;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("Timer task started at:"+new Date());
        completeTask();
        System.out.println("Timer task finished at:"+new Date());
    }

    private void completeTask() {
        try {
            //assuming it takes 20 secs to complete the task
        	System.out.println("Thread started processing...");
            Thread.sleep(10000);
            System.out.println("Thread finished processing...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]){
        TimerTask timerTask = new MyTimerTask();
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        System.out.println("Threads count: "+Thread.activeCount());
        timer.schedule(timerTask, 0);
        System.out.println("Threads count: "+Thread.activeCount());
        System.out.println("TimerTask started");
        //cancel after sometime
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }       
        
        timer.cancel();
        System.out.println("Threads count: "+Thread.activeCount());
        try {
            Thread.sleep(10000);
            //new Thread().
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    
        System.out.println("Threads count: "+Thread.activeCount());
        System.out.println("TimerTask cancelled");
    }

}
