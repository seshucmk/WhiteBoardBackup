
package com.seshu.threads;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadTimerHandler implements Runnable {	
	
	private int timeOut = 0;
	private String testValue;
	
	public ThreadTimerHandler(String value, int timeOut){
		this.timeOut = timeOut;
		this.testValue = value;
	}
	
	@Override
    public void run() {
		System.out.println("PLThreadAgent Handler: About to schedule. Threads count: "+Thread.activeCount());
		System.out.println("Current Thread: "+Thread.currentThread().getName());
		System.out.println("Current Thread Group: "+Thread.currentThread().getThreadGroup());
        Timer timer = new Timer(true);
        ThreadTimer threadTimer = new ThreadTimer(this.testValue); 
        timer.schedule(threadTimer, 0);
        try {
        	System.out.println("PLThreadAgent Handler: About to sleep. Threads count: "+Thread.activeCount());
            Thread.sleep(this.timeOut);
            System.out.println("PLThreadAgent Handler: Woke up");
        } catch (InterruptedException e) {
        	System.out.println("PLThreadAgent Handler: sleep interrupted");
            e.printStackTrace();
        }
        System.out.println("PLThreadAgent Handler: Cancelling the timer.. Threads count: "+Thread.activeCount());
        timer.cancel();
        System.out.println("PLThreadAgent Handler: Cancelled the timer.. Threads count: "+Thread.activeCount());
        System.out.println("Current Thread: "+Thread.currentThread().getName());
		System.out.println("Current Thread Group: "+Thread.currentThread().getThreadGroup());
    }
	
	public class ThreadTimer extends TimerTask {
		
		private String storeMe;	
			
	    public ThreadTimer(String storeMe) {
			this.storeMe = storeMe;
		}

		@Override
	    public void run() {
			System.out.println("Timer: About to call thread agent execute. Threads count: "+Thread.activeCount());
			System.out.println("Current Thread: "+Thread.currentThread().getName());
			System.out.println("Current Thread Group: "+Thread.currentThread().getThreadGroup());
			if(storeMe != null)
				System.out.println("Displaying variable value: "+storeMe);
			System.out.println("PLThread Timer: execution completed");
	    }
	}
}
