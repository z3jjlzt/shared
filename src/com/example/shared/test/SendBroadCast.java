package com.example.shared.test;

import com.example.shared.R;
import com.example.shared.R.id;
import com.example.shared.R.layout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendBroadCast extends Activity {
	private String ACTION_MY = "com.z3jjlzt.shared.SendBroadCast";
	private String s;
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		// receivebroadcast receiver = new receivebroadcast();
		// IntentFilter intentFilter = new IntentFilter(ACTION_MY);
		// registerReceiver(receiver, intentFilter);
	}

	private void init() {
		setContentView(R.layout.activity_sendbroadcast);
		Button button = (Button) findViewById(R.id.button1);
		editText = (EditText) findViewById(R.id.editText1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				send();
			}

			private void send() {
				s = editText.getText().toString();
				Intent intent = new Intent(ACTION_MY);
				intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
				intent.putExtra("s", s);
				sendBroadcast(intent);
			}
		});
	}

	class receivebroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			Toast.makeText(context, "接收到广播:" + intent.getStringExtra("s"), Toast.LENGTH_SHORT).show();
		}

	}

}
