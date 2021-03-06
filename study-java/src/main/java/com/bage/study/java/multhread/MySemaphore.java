package com.bage.study.java.multhread;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.bage.study.java.multhread.officialdemo.Pool;

public class MySemaphore {

	public static void main(String[] args) {
		
//		defaultDemo();
//		
//		 // 信号量共10个
//	    System.out.println("自定义demo ： 信号量共10个");
//		Semaphore semaphore = new Semaphore(10);
//	    driveCar(semaphore);
	    
	    myCase(); // 简单设置多线程

	    // rateLimiter
	}
	private static void myCase() {
		try {
	    	Semaphore semaphore = new Semaphore(10);
	    	System.out.println(semaphore.availablePermits());
			semaphore.acquire();
			System.out.println(semaphore.availablePermits());
			semaphore.release();
			System.out.println(semaphore.availablePermits());
			
//			semaphore.tryAcquire(1, 5, TimeUnit.SECONDS);
//			System.out.println("超时：" + semaphore.availablePermits());
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void defaultDemo() {
		try {
			final Pool pool = new Pool();
			System.out.println(pool.getItem());
			
			// 5 s后，放入一个 1
			new Thread(){
				public void run() {
					try {
						Thread.sleep(5000);
						pool.putItem('1');
						System.out.println("pool.putItem('5');");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}.start();
			
			System.out.println(pool.getItem());
			System.out.println(pool.getItem()); 
			System.out.println(pool.getItem()); // 最大能用三个，此处会等到 pool.putItem('1'); 后才会执行
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public static void driveCar(Semaphore semaphore) {
	        try {
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.acquire();
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.acquire(2);
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.acquire(3);
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.acquire(4);
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.release();
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.release(2);
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.release(3);
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            semaphore.release(4);
	            System.out.println("Semaphore available permits: " + semaphore.availablePermits());
	            
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	 
		
		public static void rateLimiter() {
			int threadCount = 10;
			final Semaphore semaphore = new Semaphore(2);
			ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
			for (int i = 0; i < threadCount; i++) {
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						try {
							semaphore.acquire();
							System.out.println("running ..." + new Date().toString());
							Thread.sleep(500);
							semaphore.release();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} // may wait
					}
				});
			}
		}
}
