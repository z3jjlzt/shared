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
 *2015年12月2日
 *云标签
 */
public class CloudText extends FrameLayout {
	/**
	 * 存储关键词
	 */
	private ArrayList<String> list = new ArrayList<String>();
	/**
	 * 宽高
	 */
	private int width, height;
	/**
	 * 显示最多个数
	 */
	private int MAX_SIZE = 10;

	/**
	 * 当前显示个数
	 */
	private int cur_size;

	/**
	 * 随机显示位置
	 */
	private ArrayList<Point> points = new ArrayList<CloudText.Point>();
	/**
	 * 随机数
	 */
	private Random random;
	/**
	 * 临时变量
	 */
	private int temX, temY;
	/**
	 * 上次动画，动画持续时间
	 */
	private long lastTime, duration = 800;
	/**
	 * TXT选中监听器
	 */
	private itemClickListener clickListener;

	/**
	 * @author z3jjlzt
	 *2015年12月2日
	 *实现接口
	 */
	public interface itemClickListener {
		public void onItemClicked(String txt);
	}

	/**
	 * @param clickListener
	 * 设置监听器
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
	 *            初始化
	 */
	void init(Context context) {
		this.cur_size = 5;
		random = new Random();
		lastTime = 01;
	}

	/**
	 * 刷新随机点
	 */
	void getNewPoint() {

		if (width > 0 && height > 0 && points != null) {
			points.clear();
			int h = height / cur_size;// 保证垂直方向不重复
			for (int i = 0; i < cur_size; i++) {
				temX = random.nextInt(width / 5 * 3);
				temY = (int) (h * i + Math.random() * h * 0.8);
				Point point = new Point(temX, temY);
				points.add(point);
			}
		}

	}

	/**
	 * 刷新
	 */
	public void reflesh() {
		if (System.currentTimeMillis() - lastTime > duration) {
			disappar();
			show();
			//Log.e("sb", System.currentTimeMillis() - lastTime + "b" + duration);
		}
	}

	/**
	 * 显示
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
	 * 消失
	 */
	private void disappar() {

		for (int i = getChildCount() - 1; i >= 0; i--) {// 倒序可以避免有时候文字没清理干净
			Animation anim = AnimationUtils.loadAnimation(getContext(), com.example.shared.R.anim.btn_hide2);
			final TextView txt = (TextView) getChildAt(i);
			if (txt.getVisibility() == View.GONE) {
				removeView(txt);
				continue;// 删除标记为GONE的文字
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
	 * 设置关键词
	 * 
	 * @param list
	 */
	public void setKeyText(ArrayList<String> list) {
		this.list = list;
	}

	/**
	 * 设置显示个数，不超过最大值
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
	 * @author z3jjlzt 2015年12月1日 随机点
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
