
package com.seshu.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteTimerTask {

    public static void main(String args[]){
    	System.out.println("Start of main method...");
    	System.out.println("ExecuteTimerTask...before handling...Threads count: "+Thread.activeCount());
    	ThreadTimerHandler handler = new ThreadTimerHandler("Go and get it...", 2000);
    	ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(handler);
		executorService.shutdown();
		try {
        	System.out.println("ExecuteTimerTask... About to sleep. Threads count: "+Thread.activeCount());
            Thread.sleep(1000);
            System.out.println("ExecuteTimerTask... Woke up");
            System.out.println("ExecuteTimerTask... sleeping again. Threads count: "+Thread.activeCount());
            Thread.sleep(3000);
            System.out.println("ExecuteTimerTask... Woke up");
        } catch (InterruptedException e) {
        	System.out.println("ExecuteTimerTask... sleep interrupted");
            e.printStackTrace();
        }
		System.out.println("ExecuteTimerTask...after handling...Threads count: "+Thread.activeCount());
    }
}