package com.z3jjlzt.demo;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.shared.LeiDa;
import com.example.shared.R;
import com.example.shared.R.id;
import com.example.shared.R.layout;
import com.example.shared.utils.EventInject;
import com.example.shared.utils.LztViewInject;
import com.example.shared.utils.ViewInjectUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LeiDaActivity extends Activity {
	@LztViewInject(R.id.ld)
	private LeiDa leida;
	private boolean flag = true;
	private int i=1;
	/**
	 * @param view
	 * ±ØÐëpublic
	 */
	@EventInject(R.id.ld)
	public void onclick(View view) {
		flag = !flag;
		leida.setSearching(flag);
		leida.setRate(i++);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myvp);
		ViewInjectUtils.inject(this);
		leida.setPoint(10, 10);
		leida.setSearching(true);
		leida.setPoint(-20, 20);
		leida.setPoint(-30, -30);
		leida.setPoint(40, -40);

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				Log.e("sb", leida.removePoint(410, -40) + "-->delete");
			}
		};
		scheduledExecutorService.schedule(task, 2, TimeUnit.SECONDS);
	}
}
