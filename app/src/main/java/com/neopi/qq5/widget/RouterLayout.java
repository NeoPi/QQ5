package com.neopi.qq5.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Author    :  NeoPi
 * Date      :  2017/11/27
 * Describe  :
 */

public class RouterLayout extends LinearLayout {

    private String TAG = "RouterLayout";
    private ViewPager viewPager ;
    private Context context ;
    private Paint mLinePaint ;
    private TabItem lastSelectItem ;
    private int lastSelectIndex = -1 ;

    private OnItemClickListener listener ;

    public RouterLayout(Context context) {
        this(context,null);
    }

    public RouterLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RouterLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(HORIZONTAL);
        this.context = context ;
        mLinePaint = new Paint() ;
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(1f);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.LTGRAY);

        // 设置调用onDraw
        setWillNotDraw(false);
    }

    public void setupViewPager (ViewPager viewPager) {
        this.viewPager = viewPager ;
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    setIconAlpha(position,positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setTabItemSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            for (int i = 0; i < count ; i++) {
                addTab(new TabItem(context, pagerAdapter.getPageTitle(i)+"",android.R.drawable.ic_menu_search));
            }
        }

        int currentItem = viewPager.getCurrentItem();
        setTabItemSelected(currentItem);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener = listener ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        int childWidth = getMeasuredWidth() / childCount;
        int height = getMeasuredHeight();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i) ;
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            lp.width = childWidth;
            lp.height = height;
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() ;
        switch (action) {
            case MotionEvent.ACTION_UP:
                onTabClick(event.getX(),event.getY());
                break;
        }

        return true ;
    }

    public void onTabClick (float x,float y) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TabItem) {
                Rect rect = new Rect() ;
                view.getHitRect(rect);
                if (rect.contains((int)x,(int)y)){
                    if (lastSelectItem != null) {
                        if (view != lastSelectItem) { // 重复点击
                            if (viewPager != null) {
                                viewPager.setCurrentItem(i,false);
                            } else {
//                                lastSelectItem.setSelected(false);
//                                ((TabItem)view).setSelected(true);
                            }
                        }
                        if (listener != null) {
                            listener.onTabReleased(lastSelectItem,lastSelectIndex);
                            if (view == lastSelectItem) { // 重复点击
                                listener.onTabRepeat((TabItem) view,i);
                            } else {
                                listener.onTabSelected((TabItem) view,i);
                            }
                        }
                    } else {
                        if (viewPager != null) {
                            viewPager.setCurrentItem(i,false);
                        } else {
//                            ((TabItem) view).setSelected(true);
                        }
                        if (listener != null) {
                            listener.onTabSelected((TabItem) view,i);
                        }
                    }
                    lastSelectItem = (TabItem) view;
                    break ;
                }
            }
        }
    }


    private void drawLine(Canvas canvas) {
        canvas.drawLine(0,0,getMeasuredWidth(),1,mLinePaint);
    }

    public void addTab(TabItem tabItem) {
        addView(tabItem);
    }

    /**
     * 选中某一个tab
     * @param tabItemSelected
     */
    public void setTabItemSelected(int tabItemSelected) {
        if (tabItemSelected < 0 || tabItemSelected >= getChildCount()) {
            return ;
        }

        TabItem childAt = (TabItem) getChildAt(tabItemSelected);
        if (childAt != null) {
            if (lastSelectItem != null) {
                if (lastSelectItem != childAt) {
                    childAt.setIconAlpha(1f);
                    lastSelectItem.setIconAlpha(0f);
                } else {

                }
            } else {
                childAt.setIconAlpha(1f);
            }
            lastSelectItem = childAt ;
        }
    }

    public void setIconAlpha(int position, float positionOffset) {
        if (position < 0 || position >= getChildCount()) {
            return ;
        }

        TabItem left = (TabItem) getChildAt(position);
        TabItem right = (TabItem) getChildAt(position + 1);
        if (left != null) {
            left.setIconAlpha(1 - positionOffset);
        }

        if (right != null) {
            right.setIconAlpha(positionOffset);
        }
    }


    public interface OnItemClickListener {

        /** 选中 */
        void onTabSelected (TabItem tabItem,int index) ;

        /** 释放 */
        void onTabReleased (TabItem tabItem,int index) ;

        /** 重复点击 */
        void onTabRepeat(TabItem tabItem,int index);
    }

}
