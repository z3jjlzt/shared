package com.example.shared;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.shared.utils.LztViewInject;
import com.example.shared.utils.ViewInjectUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LeiDaActivity extends Activity{
	@LztViewInject(R.id.ld)
	private LeiDa leida;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
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
			Log.e("sb",leida.removePoint(410, -40)+"dfd");
		}
	};
	scheduledExecutorService.schedule(task, 2,TimeUnit.SECONDS);
}
}
