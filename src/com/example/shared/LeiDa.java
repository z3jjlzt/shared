package com.example.shared;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class LeiDa extends View {
	/**
	 * 默认点
	 */
	@SuppressWarnings("unused")
	private Bitmap pointDefault;
	/**
	 * 新发现的点
	 */
	private Bitmap pointNew;
	/**
	 * 画笔
	 */
	private Paint mPaint;
	/**
	 * 执行扫描运动的图片
	 */
	private Bitmap mScanBmp;
	/**
	 * 上下文
	 */
	private Context mContext;
	/**
	 * 宽高
	 */
	private int width, height;
	/**
	 * 内外圆半径
	 */
	private int outRadius, inRadius;
	/**
	 * 圆心
	 */
	private int mx, my;
	/**
	 * 是否工作
	 */
	private boolean isSearch = false;
	/**
	 * 旋转偏移量
	 */
	private int mOffsetArgs = 0;
	/**
	 * 数量
	 */
	private int mCount = 0;
	/**
	 * 存放位置
	 */
	private ArrayList<pointBean> list = new ArrayList<pointBean>();
	/**
	 * 旋转速率
	 */
	private int rate = 3;

	public LeiDa(Context context) {
		super(context);
		init(context);
	}

	public LeiDa(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LeiDa(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mPaint = new Paint();
		this.mContext = context;
		this.pointDefault = Bitmap.createBitmap(
				BitmapFactory.decodeResource(mContext.getResources(), R.drawable.radar_default_point_ico));
		this.pointNew = Bitmap
				.createBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.radar_light_point_ico));
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
		outRadius = Math.min(width, height) / 2;
		inRadius = outRadius / 4;

		int radius = Math.min(width, height);
		mx = radius / 2;
		my = radius / 2;
		setMeasuredDimension(radius, radius);
		if (outRadius > 0)// 必须加 不然报null错误
			this.mScanBmp = Bitmap.createScaledBitmap(
					BitmapFactory.decodeResource(getResources(), R.drawable.radar_scan_img), radius, radius, false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(0xffB8DCFC);
		// 画4个圆
		canvas.drawCircle(mx, my, outRadius, mPaint);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(0xff3278B4);
		canvas.drawCircle(mx, my, inRadius * 3, mPaint);
		canvas.drawCircle(mx, my, inRadius * 2, mPaint);
		canvas.drawCircle(mx, my, inRadius * 1, mPaint);

		double radianStart = Math.toRadians((double) 45);// 将角度转换为弧度
		double radianEnd = Math.toRadians((double) 180);
		for (int i = 0; i < 4; i++)// 画出对角线
			canvas.drawLine((float) (mx + outRadius * Math.cos(radianStart * i)),
					(float) (my + outRadius * Math.sin(radianStart * i)),
					(float) (mx + outRadius * Math.cos(radianEnd + radianStart * i)),
					(float) (my + outRadius * Math.sin(radianEnd + radianStart * i)), mPaint);

		canvas.save();// 用来保存Canvas的状态.save之后，可以调用Canvas的平移、放缩、旋转等操作.
		if (isSearch) {
			canvas.rotate(mOffsetArgs, mx, my);
			canvas.drawBitmap(mScanBmp, mx - mScanBmp.getWidth() / 2, my - mScanBmp.getHeight() / 2, mPaint);
			mOffsetArgs += rate;
			this.invalidate();
		} else {
			canvas.drawBitmap(mScanBmp, mx - mScanBmp.getWidth() / 2, my - mScanBmp.getHeight() / 2, mPaint);
		}
		canvas.restore();// 恢复
		if (mCount > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				canvas.drawBitmap(pointNew, mx - pointNew.getWidth() / 2 + list.get(i).getOffX(),
						my - pointNew.getHeight() / 2 - list.get(i).getOffY(), null);

			}
		}
	}

	/**
	 * 
	 * @param offX偏移圆心X
	 * @param offY偏移圆心Y
	 *            设置发现的点
	 */
	public void setPoint(int offX, int offY) {
		pointBean beans = new pointBean();
		beans.setOffX(offX);
		beans.setOffY(offY);
		beans.setId(mCount);
		list.add(beans);
		mCount++;
	}

	/**
	 * @param status
	 *            是否扫描
	 */
	public void setSearching(boolean status) {
		this.isSearch = status;
		invalidate();
	}

	/**
	 * 
	 * @param offX偏移圆心X
	 * @param offY偏移圆心Y
	 *            删除发现的点
	 */
	public boolean removePoint(int offX, int offY) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (list.get(i).getOffX() == offX && list.get(i).getOffY() == offY) {
				list.remove(list.get(i));
				mCount--;
				return true;
			}
		}
		return false;

	}

	/**
	 * @param rate
	 *            设置旋转速度
	 */
	public void setRate(int rate) {
		this.rate = rate;

	}

	class pointBean {
		private int id;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		private int offX;
		private int offY;

		public int getOffX() {
			return offX;
		}

		public void setOffX(int offX) {
			this.offX = offX;
		}

		public int getOffY() {
			return offY;
		}

		public void setOffY(int offY) {
			this.offY = offY;
		}
	}

}
