package com.dd.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * 自适应比例View
 * <p>
 * Created by dexian on 2016/12/9.
 */
public class AutoAdjustView extends View {

    private AutoAdjustHelper mHelper;
    private boolean isAutoAdjust = true;

    public AutoAdjustView(Context context) {
        this(context, null);
    }

    public AutoAdjustView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelper = new AutoAdjustHelper();
        init(context, attrs);
    }

    public AutoAdjustView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new AutoAdjustHelper();
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHelper.init(context, attrs);
    }

    public void setAdjustType(int type) {
        mHelper.setAdjustType(type);
    }

    public void setScaleRate(float scale) {
        mHelper.setScale(scale);
    }

    public void setAutoAdjust(boolean isAutoAdjust) {
        this.isAutoAdjust = isAutoAdjust;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isAutoAdjust) {
            mHelper.onMeasureView(widthMeasureSpec, widthMeasureSpec);
            super.onMeasure(mHelper.getWidthSpec(), mHelper.getHeightSpec());
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
