package com.example.shared;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author z3jjlzt
 *2015��12��2��
 *�Ʊ�ǩ
 */
public class CloudText extends FrameLayout {
	/**
	 * �洢�ؼ���
	 */
	private ArrayList<String> list = new ArrayList<String>();
	/**
	 * ���
	 */
	private int width, height;
	/**
	 * ��ʾ������
	 */
	private int MAX_SIZE = 10;

	/**
	 * ��ǰ��ʾ����
	 */
	private int cur_size;

	/**
	 * �����ʾλ��
	 */
	private ArrayList<Point> points = new ArrayList<CloudText.Point>();
	/**
	 * �����
	 */
	private Random random;
	/**
	 * ��ʱ����
	 */
	private int temX, temY;
	/**
	 * �ϴζ�������������ʱ��
	 */
	private long lastTime, duration = 800;
	/**
	 * TXTѡ�м�����
	 */
	private itemClickListener clickListener;

	/**
	 * @author z3jjlzt
	 *2015��12��2��
	 *ʵ�ֽӿ�
	 */
	public interface itemClickListener {
		public void onItemClicked(String txt);
	}

	/**
	 * @param clickListener
	 * ���ü�����
	 */
	public void setitemClickListener(itemClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public CloudText(Context context) {
		super(context);
		init(context);
	}

	public CloudText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CloudText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	/**
	 * @param context
	 *            ��ʼ��
	 */
	void init(Context context) {
		this.cur_size = 5;
		random = new Random();
		lastTime = 01;
	}

	/**
	 * ˢ�������
	 */
	void getNewPoint() {

		if (width > 0 && height > 0 && points != null) {
			points.clear();
			int h = height / cur_size;// ��֤��ֱ�����ظ�
			for (int i = 0; i < cur_size; i++) {
				temX = random.nextInt(width / 5 * 3);
				temY = (int) (h * i + Math.random() * h * 0.8);
				Point point = new Point(temX, temY);
				points.add(point);
			}
		}

	}

	/**
	 * ˢ��
	 */
	public void reflesh() {
		if (System.currentTimeMillis() - lastTime > duration) {
			disappar();
			show();
			//Log.e("sb", System.currentTimeMillis() - lastTime + "b" + duration);
		}
	}

	/**
	 * ��ʾ
	 */
	private void show() {
		getNewPoint();

		for (int i = 0; i < cur_size; i++) {

			Animation anim = AnimationUtils.loadAnimation(getContext(), com.example.shared.R.anim.btn_show2);
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			lp.gravity = Gravity.LEFT | Gravity.TOP;
			lp.leftMargin = points.get(i).getX();
			lp.topMargin = points.get(i).getY();
			final TextView txt = new TextView(getContext());
			int ranColor = 0xff000000 | random.nextInt(0x0077ffff);
			int txtSize = 14 + random.nextInt(24 - 14 + 1);
			final String text = list.get(random.nextInt(list.size())).toString();
			txt.setText(text);
			txt.setShadowLayer(2, 2, 2, 0xff696969);
			txt.setTextColor(ranColor);
			txt.setTextSize(txtSize);
			txt.setGravity(Gravity.CENTER);
			addView(txt, lp);
			if (clickListener != null) {
				txt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						clickListener.onItemClicked(text);
					}
				});
			}
			txt.startAnimation(anim);
		}
		lastTime = System.currentTimeMillis();
	}

	/**
	 * ��ʧ
	 */
	private void disappar() {

		for (int i = getChildCount() - 1; i >= 0; i--) {// ������Ա�����ʱ������û����ɾ�
			Animation anim = AnimationUtils.loadAnimation(getContext(), com.example.shared.R.anim.btn_hide2);
			final TextView txt = (TextView) getChildAt(i);
			if (txt.getVisibility() == View.GONE) {
				removeView(txt);
				continue;// ɾ�����ΪGONE������
			}

			txt.startAnimation(anim);
			anim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					txt.setVisibility(View.GONE);
					txt.setOnClickListener(null);
				}
			});
		}

	}

	/**
	 * ���ùؼ���
	 * 
	 * @param list
	 */
	public void setKeyText(ArrayList<String> list) {
		this.list = list;
	}

	/**
	 * ������ʾ���������������ֵ
	 * 
	 * @param size
	 */
	public void setSize(final int size) {
		// removeAllViews();
		if (size <= MAX_SIZE)
			cur_size = size;
		else
			cur_size = MAX_SIZE;
		reflesh();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
			width = widthSize;
		} else {
			width = Math.min(getSuggestedMinimumWidth(), widthSize);
		}
		if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
			height = heightSize;
		} else {
			height = Math.min(getSuggestedMinimumHeight(), heightSize);
		}
		// Log.e("sb", width+"-"+height);
	}

	/**
	 * @author z3jjlzt 2015��12��1�� �����
	 */
	class Point {
		int x, y;

		public Point() {
			super();
		}

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

	}

}
