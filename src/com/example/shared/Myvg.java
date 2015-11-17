package com.example.shared;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class Myvg extends ViewGroup {

	@SuppressWarnings("unused")
	private Context context;

	public Myvg(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	public Myvg(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Myvg(Context context) {
		super(context);
	}

	@Override
	public int getChildCount() {
		return super.getChildCount();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			v.layout(l, t, r, b);
		}

	}

	void addview() {

	}
}
