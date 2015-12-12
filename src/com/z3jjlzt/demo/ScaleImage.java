package com.z3jjlzt.demo;

import com.example.shared.R;
import com.example.shared.R.drawable;
import com.example.shared.R.id;
import com.example.shared.R.layout;
import com.example.shared.utils.EventInject;
import com.example.shared.utils.LztViewInject;
import com.example.shared.utils.ViewInjectUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @author z3jjlzt 2015��11��28�� ���ַ�ʽ����
 */
public class ScaleImage extends Activity implements OnTouchListener {
	@LztViewInject(R.id.iv)
	private ImageView iv;
	@LztViewInject(R.id.button1)
	private Button btn;

	private Matrix matrix;
	/**
	 * �����ϴε����ű���
	 */
	Matrix savedMatrix = new Matrix();
	private Bitmap bitmap;
	private int w, h;

	// ģʽ
	static final int NONE = 0;
	static final int ZOOM = 1;
	int mode = NONE;

	float oldDist;
	/**
	 * ���Ƽ���
	 */
	private ScaleGestureDetector mScaleGestureDetector = null;

	@EventInject(R.id.button1)
	public void onclick(View view) {
		btn.setEnabled(false);
		new CountDownTimer(30000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				btn.setText(millisUntilFinished / 1000 + "s");
			}

			@Override
			public void onFinish() {
				btn.setText("redo");
				btn.setEnabled(true);

			}
		}.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scaleimage);
		ViewInjectUtils.inject(this);
		init();

	}

	private void init() {
		iv.setScaleType(ScaleType.MATRIX);// �������ŷ�ʽ
		mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureListener());
		matrix = new Matrix();
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
		w = wm.getDefaultDisplay().getWidth();
		h = wm.getDefaultDisplay().getHeight();
		iv.setImageBitmap(bitmap);
		iv.setOnTouchListener(this);

	}

	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// ImageView myImageView = (ImageView) v;
	// switch (event.getAction() & MotionEvent.ACTION_MASK) {
	//
	// case MotionEvent.ACTION_DOWN:
	// case MotionEvent.ACTION_UP:
	// case MotionEvent.ACTION_POINTER_UP:
	// mode = NONE;
	// break;
	//
	// // ���ö�㴥��ģʽ
	// case MotionEvent.ACTION_POINTER_DOWN:
	// myImageView.setScaleType(ScaleType.MATRIX);//
	// oldDist = spacing(event);
	// mode = ZOOM;
	// if (oldDist > 10f) {
	// savedMatrix.set(matrix);
	// }
	// break;
	// case MotionEvent.ACTION_MOVE:
	//
	// // ��ΪZOOMģʽ��������������
	// if (mode == ZOOM) {
	// float newDist = spacing(event);
	// if (Math.abs(newDist - oldDist) > 6f) {
	// matrix.set(savedMatrix);
	// float scale = newDist / oldDist;
	// // ���ñ�����ͼƬ���е�λ��
	// matrix.postScale(scale, scale, w / 2, h / 2);
	// myImageView.setImageMatrix(matrix);
	// }
	// }
	// break;
	// }
	// return true;
	// }

	// �����ƶ�����
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		Log.e("sb", FloatMath.sqrt(x * x + y * y) + "");
		return FloatMath.sqrt(x * x + y * y);
	}

	// �����е�λ��
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {

			// ���ű���
			float scale = detector.getScaleFactor();
			matrix.set(savedMatrix);
			matrix.postScale(scale, scale, w / 2, h / 2);
			iv.setImageMatrix(matrix);
			return false;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {

			// һ��Ҫ����true�Ż����onScale()�������
			savedMatrix.set(matrix);
			return true;
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mScaleGestureDetector.onTouchEvent(event);
	}
}
