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
 * ����˵�������Ժ�Ŀ����о�����ʹ��ScheduledExecutorService(JDK1.5�Ժ�)���Timer��
 * 
 * @author z3jjlzt Javaͨ��Executors�ṩ�����̳߳أ��ֱ�Ϊ��
 *         newCachedThreadPool����һ���ɻ����̳߳أ�����̳߳س��ȳ���������Ҫ���������տ����̣߳����޿ɻ��գ����½��̡߳�
 *         newFixedThreadPool ����һ�������̳߳أ��ɿ����߳���󲢷������������̻߳��ڶ����еȴ���
 *         newScheduledThreadPool ����һ�������̳߳أ�֧�ֶ�ʱ������������ִ�С�
 *         newSingleThreadExecutor
 *         ����һ�����̻߳����̳߳أ���ֻ����Ψһ�Ĺ����߳���ִ�����񣬱�֤����������ָ��˳��(FIFO, LIFO, ���ȼ�)ִ�С�
 *
 */
public class ExecutorServiceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scheduledThreadPoolTest();
	}

	/**
	 * ����һ�������̳߳أ��ɿ����߳���󲢷������������̻߳��ڶ����еȴ���
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
	 * ��ʾ�ӳ�1���ÿ3��ִ��һ�Ρ�
	 * 
	 * ScheduledExecutorService��Timer����ȫ�����ܸ�ǿ�󣬺������һƪ�������жԱȡ�
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
