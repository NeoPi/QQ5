package com.neopi.qq5.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Author    :  NeoPi
 * Date      :  2017/11/25
 * Describe  :
 */

public class TabItem extends View {

    private Paint mTextPaint ;
    private Paint mIconPaint ;
    private Paint mTipsPaint ;

    private Bitmap mIcon ;
    private int DEFAULT_ICON_WIDTH = 100 ;
    private int iconWidth ;
    private int iconPadding = 0 ;
    private float alpha = 0f ;

    private Rect textBound ;
    private Rect iconRect ;

    private String title = "#" ;
    private int drawableId ;

    private final String INSTANCE_STATUS = "instance_status" ;
    private final String INSTANCE_ALPHA = "instance_alpha" ;

    public TabItem(Context context) {
        this(context,null);
    }

    public TabItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs,defStyleAttr);
    }

    public TabItem (Context context ,String title , @DrawableRes int drawableId) {
        this(context) ;
        this.title = title ;
        this.drawableId = drawableId ;
        initData();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        mTextPaint = new Paint() ;
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(36f);

        mIconPaint = new Paint();
        mIconPaint.setAntiAlias(true);
        mIconPaint.setFilterBitmap(true);


        mTipsPaint = new Paint();
        mTipsPaint.setColor(Color.RED);
        mTipsPaint.setAntiAlias(true);


        initData();
    }

    private void initData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = DEFAULT_ICON_WIDTH ;
        options.outWidth = DEFAULT_ICON_WIDTH ;

        if (drawableId == 0) {
            mIcon = Bitmap.createBitmap(DEFAULT_ICON_WIDTH,DEFAULT_ICON_WIDTH, Bitmap.Config.RGB_565) ;
        } else {
            mIcon = BitmapFactory.decodeResource(getResources(), drawableId,options) ;
        }

        textBound = new Rect() ;
        mTextPaint.getTextBounds(title,0,title.length(),textBound);

        iconRect = new Rect() ;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - textBound.height() - iconPadding);

        iconWidth = Math.min(iconWidth,DEFAULT_ICON_WIDTH) ;

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int xOffset = (height - iconWidth - iconPadding - textBound.height()) / 2;

        int left = (width - iconWidth ) / 2 ;

        iconRect.set(left,xOffset,iconWidth + left ,xOffset + iconWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("111","onDraw") ;
        drawIcon(canvas);
        drawSourceText(canvas);
        drawTargetText(canvas);
        drawTips(canvas);
        setupBitmap(canvas);
    }

    /**
     * 在内存中准备mBitmap绘制纯色 设置 xfermode 取色
     *
     * setAlpha -> 纯色 -> xfermode -> 绘制图标
     */
    private void setupBitmap(Canvas canvas) {

        int xAlpha = (int) Math.ceil(alpha * 255);

        Bitmap xBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888) ;
        Canvas xCanvas = new Canvas(xBitmap) ;
        Paint xPaint = new Paint();
        xPaint.setColor(Color.GREEN);
        xPaint.setAntiAlias(true);
        xPaint.setDither(true);
        xPaint.setAlpha(xAlpha);

        xCanvas.drawRect(iconRect,xPaint);
        xPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN)) ;

        xPaint.setAlpha(255);
        xCanvas.drawBitmap(mIcon,null,iconRect,xPaint);

        canvas.drawBitmap(xBitmap,0,0,null);
    }

    private void drawIcon(Canvas canvas) {
        canvas.drawBitmap(mIcon,null,iconRect,mIconPaint);
    }

    private void drawSourceText(Canvas canvas) {

        int xAlpha = (int) Math.ceil(alpha * 255);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAlpha(255 - xAlpha);

        int messageLong = textBound.width();

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int yOffset = (height - iconRect.height() - iconPadding - textBound.height()) / 2;

        canvas.drawText(title,
                (width - messageLong) / 2,
                iconRect.height() + yOffset + iconPadding + textBound.height() ,
                mTextPaint );
    }

    /**
     * 绘制变色文本
     *
     * @param canvas
     */
    private void drawTargetText(Canvas canvas) {

        int xAlpha = (int) Math.ceil(alpha * 255);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setAlpha(xAlpha);

        int messageLong = textBound.width();

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int yOffset = (height - iconRect.height() - iconPadding - textBound.height()) / 2;

        canvas.drawText(title,
                (width - messageLong) / 2,
                iconRect.height() + yOffset + iconPadding + textBound.height() ,
                mTextPaint );
    }

    /**
     * 红点
     * @param canvas
     */
    private void drawTips(Canvas canvas) {

    }

    public void setText(String title) {
        if (TextUtils.isEmpty(title)) {
            title = "" ;
        }
        this.title = title ;
        invalidate();
    }

    public void setIconAlpha(float alpha) {
        this.alpha = alpha;
        invalidate();
    }

    public void setText(@StringRes int strId) {
        setText(getResources().getString(strId));
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            alpha = ((Bundle) state).getFloat(INSTANCE_ALPHA) ;
            Parcelable parcelable = ((Bundle) state).getParcelable(INSTANCE_STATUS);
            super.onRestoreInstanceState(parcelable);
            return ;

        }
        super.onRestoreInstanceState(state);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle() ;
        bundle.putFloat(INSTANCE_ALPHA,alpha);
        bundle.putParcelable(INSTANCE_STATUS,super.onSaveInstanceState());
        return bundle;
    }
}
