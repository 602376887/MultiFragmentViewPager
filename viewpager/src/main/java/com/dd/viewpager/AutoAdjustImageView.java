package com.dd.viewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * 自适应比例ImageView
 * <p>
 * Created by dexian on 2015/5/27.
 */
public class AutoAdjustImageView extends ImageView {

    private AutoAdjustHelper mHelper;
    private int mCustWidth;
    private int mCustHeight;
    private boolean isAutoAdjust = true;


    public AutoAdjustImageView(Context context) {
        this(context, null);
    }

    public AutoAdjustImageView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        mHelper = new AutoAdjustHelper();
        init(context, attrs);

    }

    public AutoAdjustImageView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new AutoAdjustHelper();
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHelper.init(context, attrs);
    }

    public void setAutoAdjust(boolean isAutoAdjust) {
        this.isAutoAdjust = isAutoAdjust;
    }

    public void setCustWidth(int width) {
        mCustWidth = width;
    }

    public void setCustHeight(int height) {
        mCustHeight = height;
    }

    public void setAdjustType(int type) {
        mHelper.setAdjustType(type);
    }

    public void setScaleRate(float scale) {
        mHelper.setScale(scale);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isAutoAdjust) {
            int custWidth = 0;
            int custHeight = 0;
            if (mCustHeight != 0 && mCustWidth != 0) {
                custWidth = mCustWidth;
                custHeight = mCustHeight;
            } else {
                Drawable drawable = getDrawable();

                if (drawable != null) {
                    custWidth = drawable.getIntrinsicWidth();
                    custHeight = drawable.getIntrinsicHeight();
                }
            }

            mHelper.setRelativeHeight(custHeight);
            mHelper.setRelativeWidth(custWidth);

            mHelper.onMeasureView(widthMeasureSpec, heightMeasureSpec);
            super.onMeasure(mHelper.getWidthSpec(), mHelper.getHeightSpec());
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
