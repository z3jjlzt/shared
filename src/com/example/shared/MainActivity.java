package com.example.shared;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText et;
	private TextView tv;
	private Button btn;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myvp);
		// getActionBar().show();

		// sp= getSharedPreferences("t", MODE_PRIVATE);
		// et=(EditText) findViewById(R.id.et);
		// tv= (TextView) findViewById(R.id.textView1);
		// String s= sp.getString("s", "Î´±£´æ");
		// tv.setText(s);
		// btn= (Button) findViewById(R.id.btn);
		// btn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// String s =et.getText().toString();
		// Editor e= sp.edit();
		// e.putString("s", s);
		// e.commit();
		//
		// }
		// });
	}

}
