package com.z3jjlzt.demo;

import java.util.ArrayList;

import com.example.shared.CloudText;
import com.example.shared.R;
import com.example.shared.CloudText.itemClickListener;
import com.example.shared.R.id;
import com.example.shared.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CloudActivity extends Activity implements itemClickListener {
	@Bind(R.id.ct)
	CloudText ct;

	int a = 5;

	@OnClick({ R.id.button2, R.id.button1 })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.button2:
			ct.setSize(a);
			a++;
			break;
		case R.id.button1:
			ct.reflesh();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud);
		// ViewInjectUtils.inject(this);
		ButterKnife.bind(this);
		String[] keywords = new String[] { "QQsdffffffffffffffffffff", "Sodffffffffffffffffino", "APK", "GFW", "Ǧ��",
				"����", "���澫��", "MacBook Pro", "ƽ�����", "��ʫ����", "��ffffffffffffffffffffffffffffffffffffffff��ŷ TR-100",
				"�ʼǱ�", "SPY Mouse", "Thinkpad E40", "�������", "�ڴ�����", "��ͼ", "����", "����", "����", "ͨѶ¼", "������",
				"CSDN leffffffffffffffffffak", "��ȫ", "3D", "��Ů", "����", "4743G", "����", "����", "ŷ��", "�����", "��ŭ��С��",
				"mmShow", "���׹�����", "iciba", "��ˮ��ϵ", "����App", "������", "365����", "����ʶ��", "Chrome", "Safari", "�й���Siri",
				"A5������", "iPhone4S", "Ħ�� ME525", "���� M9", "�῵ S2500" };
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < keywords.length; i++)
			list.add(keywords[i]);
		ct.setKeyText(list);
		ct.setitemClickListener(this);
		;

	}

	@Override
	public void onItemClicked(String txt) {
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
	}
}
