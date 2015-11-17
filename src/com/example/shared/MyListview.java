package com.example.shared;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * @author z3jjlzt 1,���캯�� 2.���� ���ƴ����࣬��ǰѡ����Ŀ���Ƿ���ʾɾ����ť�� ɾ����ť��group 3.��ʼ�������࣬���ü���
 *         4.�ж��Ƿ����BTN��
 *
 */
@SuppressLint({ "InflateParams", "ClickableViewAccessibility" })
public class MyListview extends ListView implements OnTouchListener, OnGestureListener {
	/**
	 * ����ʶ��
	 */
	private GestureDetector gestureDetector;
	/**
	 * ѡ����Ŀ
	 */
	private int selectedItem;
	/**
	 * �Ƿ���ʾɾ����ť
	 */
	private boolean isBtnShow;
	/**
	 * ɾ����ť
	 */
	private Button btnDel;
	/**
	 * listview��ÿһ��item�Ĳ���
	 */
	private ViewGroup viewGroup;
	/**
	 * ɾ������
	 */
	private onItemDeletedListener itemDeletedListener;

	public interface onItemDeletedListener {
		public void onItemDelete(int pos);
	};

	public MyListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		gestureDetector = new GestureDetector(getContext(), this);
		setOnTouchListener(this);
	}

	public MyListview(Context context) {
		this(context, null);
	}

	public MyListview(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public void setOnItemDeleteListener(onItemDeletedListener ItemDeletedListener) {
		this.itemDeletedListener = ItemDeletedListener;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.e("sb", "ondown");
		// �õ���ǰ��������Ŀ
		if (!isBtnShow) {
			selectedItem = pointToPosition((int) e.getX(), (int) e.getY());
		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.e("sb", "onshowpress");
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.e("sb", "onsingletapup");
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.e("sb", "onscroll");

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Log.e("sb", "onlongpress");
	}

	/*
	 * (non-Javadoc) ��Ҫ���⴦�� velocityX �����ƶ��ٶ�
	 * 
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.
	 * MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.e("sb", "onfling");

		if (!isBtnShow && (-velocityX > Math.abs(velocityY))) {
			btnDel = (Button) LayoutInflater.from(getContext()).inflate(R.layout.btn, null);
			btnDel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					viewGroup.removeView(btnDel);
					btnDel = null;
					isBtnShow = false;
					itemDeletedListener.onItemDelete(selectedItem);
				}
			});

			viewGroup = (ViewGroup) getChildAt(selectedItem - getFirstVisiblePosition());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.CENTER_VERTICAL);
			btnDel.setLayoutParams(params);

			viewGroup.addView(btnDel);
			btnDel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.btn_show));
			isBtnShow = true;
		} else {
			setOnTouchListener(this);
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		selectedItem = pointToPosition((int) event.getX(), (int) event.getY());
		if (isBtnShow) {
			// btnDel.startAnimation(AnimationUtils.loadAnimation(getContext(),
			// R.anim.btn_show));
			viewGroup.removeView(btnDel);// �Ƴ�ɾ����ť
			btnDel = null;
			isBtnShow = false;
			return false;
		} else {
			return gestureDetector.onTouchEvent(event);
		}
	}

}
