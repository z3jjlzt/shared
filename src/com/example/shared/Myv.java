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

public class Myv extends View {
	private String mytitleText;
	private int mytitleTextColor;
	private int mytitleTextSize;
	private int mytitleTextBg;
	private int a;//????
	private int b;

	private Rect mrect;
	private Paint mpaint; 

	public Myv(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typearray = context.obtainStyledAttributes(attrs, R.styleable.Myv, defStyleAttr, 0);
		int count = typearray.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = typearray.getIndex(i);
			switch (attr) {
			case R.styleable.Myv_mytitleText:
				this.mytitleText = typearray.getString(attr);

				break;
			case R.styleable.Myv_mytitleTextColor:
				this.mytitleTextColor = typearray.getColor(attr, Color.BLACK);
				break;
			case R.styleable.Myv_mytitleTextSize:
				this.mytitleTextSize = typearray.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				
				break;
			case R.styleable.Myv_mytitleTextBg:
				this.mytitleTextBg=typearray.getColor(attr, Color.RED);
				
				break;


			default:
				break;
			}
		}
		typearray.recycle();
		mpaint = new Paint();
		mpaint.setTextSize(mytitleTextSize);
		// mpaint.setColor(mytitleTextColor);
		mrect = new Rect();
		mpaint.getTextBounds(mytitleText, 0, mytitleText.length(), mrect);
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				todo();
			}
		});
	}

	 void todo() {
		int random= new Random().nextInt(1000);
		mytitleText=Integer.toString(random);
		Log.e("sb", mytitleText);
		postInvalidate();
	}

	public Myv(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Myv(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		a=widthMeasureSpec;
		b=heightMeasureSpec;
		int wsize = MeasureSpec.getSize(widthMeasureSpec);
		int wmode = MeasureSpec.getMode(widthMeasureSpec);
		int hsize = MeasureSpec.getSize(heightMeasureSpec);
		int hmode = MeasureSpec.getMode(heightMeasureSpec);
		int finalwidth;
		int finalheight;
		if (wmode == MeasureSpec.EXACTLY) {
			finalwidth = wsize;
		} else {
			mpaint.setTextSize(mytitleTextSize);
			mpaint.getTextBounds(mytitleText, 0, mytitleText.length(), mrect);
			float tem = mrect.width();
			int t = (int) (getPaddingLeft() + tem + getPaddingRight());
			finalwidth = t;
		}
		if (hmode == MeasureSpec.EXACTLY) {
			finalheight = hsize;
		} else {
			mpaint.setTextSize(mytitleTextSize);
			mpaint.getTextBounds(mytitleText, 0, mytitleText.length(), mrect);
			float tem = mrect.height();
			int t = (int) (getPaddingTop() + tem + getPaddingBottom());
			finalheight = t;
		}
		setMeasuredDimension(finalwidth, finalheight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//Log.e("sb", mytitleTextBg+"");
		 mpaint.setColor(mytitleTextBg);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mpaint);

		mpaint.setColor(mytitleTextColor);
		canvas.drawText(mytitleText, getWidth() / 2 - mrect.width() / 2, getHeight() / 2 + mrect.height() / 2, mpaint);

	}

}
