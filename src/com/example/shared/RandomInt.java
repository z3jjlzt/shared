package com.example.shared;

import java.util.Random;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class RandomInt extends View {
	private int mBgColor;
	private String mInt;
	private int mTextColor;
	private int mTextSize;

	private Rect mBound;
	private Paint mPaint;//changed
	private int radius;

	public RandomInt(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.RandomInt, defStyleAttr, 0);
		int count = arr.getIndexCount();
		for (int i = 0; i < count; i++) {
			int index = arr.getIndex(i);
			switch (index) {
			case R.styleable.RandomInt_myInt:
				this.mInt = arr.getString(index);
				break;
			case R.styleable.RandomInt_myBgColor:
				this.mBgColor = arr.getColor(index, Color.RED);
				break;
			case R.styleable.RandomInt_myTextColor:
				this.mTextColor = arr.getColor(index, Color.GREEN);
				break;
			case R.styleable.RandomInt_myTextSize:
				this.mTextSize = arr.getDimensionPixelSize(index, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				break;

			default:
				break;
			}
		}
		arr.recycle();
		mPaint = new Paint();
		mPaint.setTextSize(mTextSize);
		mBound = new Rect();
		mPaint.getTextBounds(mInt, 0, mInt.length(), mBound);
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				todo();
			}

		});
	}

	private void todo() {
		int x = new Random().nextInt(99999);
		mInt = Integer.toString(x);
		mPaint.setTextSize(mTextSize);
		mPaint.getTextBounds(mInt, 0, mInt.length(), mBound);
		LayoutParams par = getLayoutParams();//重新设置宽高
		par.width=getPaddingLeft() + mBound.width() + getPaddingRight();
		par.height= getPaddingTop() + mBound.height() + getPaddingBottom();
		setLayoutParams(par);
		postInvalidate();
	}

	public RandomInt(Context context) {
		this(context, null);
	}

	public RandomInt(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int fWidth;
		int fHeight;
		int wSize = MeasureSpec.getSize(widthMeasureSpec);
		int wMode = MeasureSpec.getMode(widthMeasureSpec);
		int hSize = MeasureSpec.getSize(heightMeasureSpec);
		int hMode = MeasureSpec.getMode(heightMeasureSpec);
		if (wMode == MeasureSpec.EXACTLY) {
			fWidth = wSize;
		} else {
			mPaint.setTextSize(mTextSize);
			mPaint.getTextBounds(mInt, 0, mInt.length(), mBound);
			int tepWidth = (int) (getPaddingLeft() + mBound.width() + getPaddingRight());
			fWidth = tepWidth;
		}
		if (hMode == MeasureSpec.EXACTLY) {
			fHeight = hSize;
		} else {

			mPaint.setTextSize(mTextSize);
			mPaint.getTextBounds(mInt, 0, mInt.length(), mBound);
			int tepHeight = getPaddingTop() + mBound.height() + getPaddingBottom();
			fHeight = tepHeight;
		}
		radius = Math.max(fHeight, fWidth) / 2;
		setMeasuredDimension(radius * 2, radius * 2);
		Log.e("sb", "onmeasure");  
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("sb", "onDraw");  
		mPaint.setColor(mBgColor);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
		mPaint.setColor(mTextColor);
		canvas.drawText(mInt, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		 Log.e("sb", "onSizeChanged,w="+w+",h="+h+",oldw="+oldw+",oldh="+oldh);  
	}

}
