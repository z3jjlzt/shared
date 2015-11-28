package com.example.shared.test;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 基本说明了在以后的开发中尽可能使用ScheduledExecutorService(JDK1.5以后)替代Timer。
 * 
 * @author z3jjlzt Java通过Executors提供四种线程池，分别为：
 *         newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 *         newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 *         newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 *         newSingleThreadExecutor
 *         创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *
 */
public class ExecutorServiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scheduledThreadPoolTest();
	}

	/**
	 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
	 */
	private void fixThreadPoolTest() {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 10; i++) {
			final int index = i;
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Log.e("sb", index + "");
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * 表示延迟1秒后每3秒执行一次。
	 * 
	 * ScheduledExecutorService比Timer更安全，功能更强大，后面会有一篇单独进行对比。
	 * 
	 */
	private void scheduledThreadPoolTest() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				try {
					Log.e("sb", "delay 3 s");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		scheduledExecutorService.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);

	}

	/**
	 * 
	 */
	private void singleThreadExecutorTest() {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			singleThreadExecutor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						Log.e("sb", index + "");
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
