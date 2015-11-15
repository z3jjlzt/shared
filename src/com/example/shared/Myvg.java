package com.example.shared;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class Myvg extends ViewGroup {

	private Context context;

	public Myvg(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;

		// TODO Auto-generated constructor stub
	}

	public Myvg(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Myvg(Context context) {
		super(context);
		// addview();svsfdsd
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stubssss
		return super.getChildCount();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			v.layout(l, t, r, b);
		}

	}

	void addview() {

	}
}
