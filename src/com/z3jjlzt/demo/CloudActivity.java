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
		String[] keywords = new String[] { "QQsdffffffffffffffffffff", "Sodffffffffffffffffino", "APK", "GFW", "铅笔",
				"短信", "桌面精灵", "MacBook Pro", "平板电脑", "雅诗兰黛", "卡ffffffffffffffffffffffffffffffffffffffff西欧 TR-100",
				"笔记本", "SPY Mouse", "Thinkpad E40", "捕鱼达人", "内存清理", "地图", "导航", "闹钟", "主题", "通讯录", "播放器",
				"CSDN leffffffffffffffffffak", "安全", "3D", "美女", "天气", "4743G", "戴尔", "联想", "欧朋", "浏览器", "愤怒的小鸟",
				"mmShow", "网易公开课", "iciba", "油水关系", "网游App", "互联网", "365日历", "脸部识别", "Chrome", "Safari", "中国版Siri",
				"A5处理器", "iPhone4S", "摩托 ME525", "魅族 M9", "尼康 S2500" };
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
