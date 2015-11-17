package com.example.shared;

import java.util.ArrayList;

import com.example.shared.MyListview.onItemDeletedListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements onItemDeletedListener {

	private MyListview lv;
	ArrayList<String> list;
	private MyAdapter adapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (MyListview) findViewById(R.id.lv);
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("itme" + i);

		}

		adapter1 = new MyAdapter(this);
		lv.setAdapter(adapter1);
		lv.setOnItemDeleteListener(new onItemDeletedListener() {

			@Override
			public void onItemDelete(int pos) {
				list.remove(pos);
				adapter1.notifyDataSetChanged();
			}
		});

	}

	@Override
	public void onItemDelete(int pos) {

	}

	class MyAdapter extends BaseAdapter {

		private Context context;

		public MyAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item, parent);
			}
			TextView textView = (TextView) convertView.findViewById(R.id.tv);
			textView.setText(list.get(position));
			return convertView;
		}
	}

}
