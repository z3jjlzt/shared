package com.example.shared.utils;

import java.util.Date;

import com.example.shared.R;
import com.example.shared.R.anim;
import com.example.shared.R.id;
import com.example.shared.R.layout;

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
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author z3jjlzt 1,构造函数 2.定义 手势处理类，当前选择条目，是否显示删除按钮， 删除按钮，group 3.初始化手势类，设置监听
 *         4.判断是否出现BTN，
 *
 */
@SuppressLint({ "InflateParams", "ClickableViewAccessibility" })
public class MyListview extends ListView implements OnTouchListener, OnGestureListener {
	/**
	 * 手势识别
	 */
	private GestureDetector gestureDetector;
	/**
	 * 选择条目
	 */
	private int selectedItem;
	/**
	 * 是否显示删除按钮
	 */
	private boolean isBtnShow;
	/**
	 * 删除按钮
	 */
	private Button btnDel;
	/**
	 * listview的每一个item的布局
	 */
	private ViewGroup viewGroup;
	/**
	 * 删除监听
	 */
	private onItemDeletedListener itemDeletedListener;
	/**
	 * 下滑刷新
	 */
	private boolean canReflash = false;
	private int height;
	/**
	 * listview 滑动监听器
	 */
	private OnScrollListener mOnScrollListener;
	/**
	 * 按下起始位置
	 */
	private int startY;
	/**
	 * 现在的位置
	 */
	private int currentY;
	/**
	 * 下拉距离
	 */
	private int pullDistance;
	/**
	 * 目前状态
	 */
	private state STATE;
	/**
	 * 回退
	 */
	private boolean isback = false;
	/**
	 * 提示
	 */
	private TextView hint;
	/**
	 * 上次刷新时间
	 */
	private TextView last_time;

	/**
	 * 头部
	 */
	private View Header;
	/**
	 * 刷新监听器
	 */
	private OnReflashListener reflashListener;

	/**
	 * @author z3jjlzt 所有状态
	 */

	public enum state {
		NORMAL, REFLASHING, PULL_TO_REFLASH, RELEASA;
	}

	public interface onItemDeletedListener {
		public void onItemDelete(int pos);
	};

	public MyListview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
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
		//Log.e("sb", "ondown");
		// 得到当前触摸的条目
		if (!isBtnShow) {
			selectedItem = pointToPosition((int) e.getX(), (int) e.getY());
		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	/*
	 * (non-Javadoc) 主要在这处理 velocityX 横向移动速度
	 * 
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.
	 * MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// Log.e("sb", "onfling");

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
			viewGroup.removeView(btnDel);// 移除删除按钮
			btnDel = null;
			isBtnShow = false;
			return false;
		} else {
			return gestureDetector.onTouchEvent(event);
		}
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mOnScrollListener = l;

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int y = (int) ev.getRawY();// 相对屏幕位置
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			startY = y;

			break;
		case MotionEvent.ACTION_MOVE:
			if (!canReflash) {
				break;
			}
			currentY = y;
			pullDistance = (currentY - startY) / 3;

			if (STATE == state.NORMAL && pullDistance > 0) {
				STATE = state.PULL_TO_REFLASH;
				changeState();
			} else if (STATE == state.PULL_TO_REFLASH && pullDistance > height) {
				STATE = state.RELEASA;
				changeState();
			} else if (STATE == state.RELEASA) {
				if (pullDistance < 0) {
					STATE = state.NORMAL;
					changeState();
				} else if (pullDistance < height) {
					STATE = state.PULL_TO_REFLASH;
					isback = true;
					changeState();
				}
			}
			if (STATE != state.REFLASHING) {
				Header.setPadding(0, (int) (pullDistance - height), 0, 0);
			}

			break;
		case MotionEvent.ACTION_UP:
			if (STATE == state.REFLASHING) {
				break;
			} else if (STATE == state.PULL_TO_REFLASH) {
				STATE = state.NORMAL;
			} else if (STATE == state.RELEASA) {
				STATE = state.REFLASHING;
			} else {
				break;
			}
			changeState();

			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	private void changeState() {
		if (STATE == state.NORMAL) {
			Header.setPadding(0, -height, 0, 0);
		} else if (STATE == state.PULL_TO_REFLASH) {
			if (isback) {
				isback = false;
			}
			hint.setText("下拉刷新");
		} else if (STATE == state.RELEASA) {
			hint.setText("松开刷新");

		} else if (STATE == state.REFLASHING) {

			hint.setText("正在刷新");
			if (reflashListener != null) {
				reflashListener.OnReflash();
			}

			Header.setPadding(0, 0, 0, 0);

		}
	}

	void init(Context context) {
		STATE = state.NORMAL;
		Header = LayoutInflater.from(context).inflate(R.layout.reflash, null);
		hint = (TextView) Header.findViewById(R.id.reflash);
		last_time = (TextView) Header.findViewById(R.id.last_time);

		measureHeaderView(Header);
		height = Header.getMeasuredHeight();
		Log.e("sb", height + "");
		Header.setPadding(0, -height, 0, 0);
		Header.invalidate();// 更新
		addHeaderView(Header);

		super.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (mOnScrollListener != null) {
					mOnScrollListener.onScrollStateChanged(view, scrollState);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if (mOnScrollListener != null) {
					mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
				}

				if (firstVisibleItem == 0) {
					canReflash = true;
				} else {
					canReflash = false;
				}
			}
		});

	}

	public interface OnReflashListener {
		public void OnReflash();
	}

	public void setOnRefreshListener(OnReflashListener listener) {
		this.reflashListener = listener;
	}

	/**
	 * 刷新完成
	 */
	@SuppressWarnings("deprecation")
	public void onReflashCompleted() {
		STATE = state.NORMAL;
		changeState();
		last_time.setText("更新时间：" + new Date().toLocaleString());
		hint.setText("下拉刷新");

	}

	/**
	 * @param view
	 *            测量 view  注意：头部XML用LINEARLAYOUT relativelayout 有BUG
	 */
	private void measureHeaderView(View view) {
		// 解决了HEADER为RELATIVELAYOUT时报NULL指针异常
		// view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT));
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
		int childMeasureHeight;
		if (lp.height > 0) {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
		} else {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(childMeasureWidth, childMeasureHeight);
	}

	

}
