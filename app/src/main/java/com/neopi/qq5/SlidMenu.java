package com.neopi.qq5;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * @TODO TODO
 * @DATE 2015年11月28日
 * @FileName SlidMenu.java
 *
 * @AUTHOR NeoPi
 */
public class SlidMenu extends HorizontalScrollView {

	private LinearLayout mWrapper; // ScrollView 的子布局
	private ViewGroup mMenu; // 左侧侧边栏
	private ViewGroup mContant; // 主布局
	private int mMenuWidth = 100; // 左侧的布局宽度
	private int mPaddingRight = 400; // 　　左侧离右边的距离
	private int mScreenWidth; // 屏幕宽度
	private boolean once = true;  // 是否是第一次

	public SlidMenu(Context context) {
		this(context, null);
	}

	public SlidMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;

		mPaddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				400, context.getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (once) {
			mWrapper = (LinearLayout) this.getChildAt(0);
			mMenu = (ViewGroup) mWrapper.getChildAt(0);
			mContant = (ViewGroup) mWrapper.getChildAt(1);

			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mPaddingRight;
			mContant.getLayoutParams().width = mScreenWidth;
			once = false;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			if (this.getScrollX() > mMenuWidth / 2) {
				closeMenu();
			} else {
				openMenu();
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 弹出左侧边栏
	 */
	private void openMenu() {
		this.smoothScrollTo(0,0);
	}

	/**
	 * 关闭左侧弹出栏
	 */
	private void closeMenu() {
		this.smoothScrollTo(mMenuWidth,0);
	}
	
	
}
