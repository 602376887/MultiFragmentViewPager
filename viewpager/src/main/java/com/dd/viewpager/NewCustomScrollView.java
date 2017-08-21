package com.dd.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;


/**
 * Created by dengdun on 2017/8/17.
 */

public class NewCustomScrollView extends ScrollView {

    private static final String TAG = NewCustomScrollView.class.getSimpleName();

    private OnScrollViewTouchListener mOnScrollViewTouchListener;
    private HeaderExpand mIsHeaderExpand = HeaderExpand.EXPAND; //默认展开
    private boolean isIntercept = true;//是否拦截
    private float lastX;
    private float lastY;

    //头部状态
    public enum HeaderExpand {
        EXPAND, MIDDLE, SHRINK
    }

    public NewCustomScrollView(Context context) {
        super(context);
        init();
    }

    public NewCustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnTouchlistener(OnScrollViewTouchListener onScrollViewTouchListener) {
        this.mOnScrollViewTouchListener = onScrollViewTouchListener;
    }

    private void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int tabTopHeight = 0;//scroView允许滑动的最大高度
        if (mOnScrollViewTouchListener != null) {
            tabTopHeight = mOnScrollViewTouchListener.getTabStripTopHeight(ev);
        }
        int action = ev.getAction();
        if (getScrollY() <= 0) {
            mIsHeaderExpand = HeaderExpand.EXPAND;
        } else if (getScrollY() >= tabTopHeight && tabTopHeight != 0) {
            mIsHeaderExpand = HeaderExpand.SHRINK;
        } else {
            mIsHeaderExpand = HeaderExpand.MIDDLE;
        }
        float x = ev.getX();
        float y = ev.getY();
        Log.i("setTouchEvent", " mIsHeaderExpand:" + mIsHeaderExpand + " ev" + ev.getAction());
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y >= lastY) {//向下滑动
                    if (mIsHeaderExpand == HeaderExpand.SHRINK) {
                        if (mOnScrollViewTouchListener != null && mOnScrollViewTouchListener.isSlidingTop(ev)) {
                            //滑动scrollview
                            setTouchEvent(ev, true);
                        } else {
                            //滑动listView
                            setTouchEvent(ev, false);
                        }
                    } else if (mIsHeaderExpand == HeaderExpand.MIDDLE) {
                        //滑动scrollview
                        setTouchEvent(ev, true);
                    } else if (mIsHeaderExpand == HeaderExpand.EXPAND) {
                        isIntercept = true;
                    }
                } else if (y < lastY) {//向上滑动
                    if (mIsHeaderExpand == HeaderExpand.SHRINK) {
                        //滑动ListView
                        setTouchEvent(ev, false);
                    } else if (mIsHeaderExpand == HeaderExpand.MIDDLE) {
                        //滑动scrollview
                        setTouchEvent(ev, true);
                    } else if (mIsHeaderExpand == HeaderExpand.EXPAND) {
                        //滑动scrollview
                        setTouchEvent(ev, true);
                    }
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                resetTouch();
                break;
            case MotionEvent.ACTION_CANCEL:
                resetTouch();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setTouchEvent(MotionEvent ev, boolean intercept) {
        Log.i("setTouchEvent", ev + " intercept:" + intercept + " isIntercept:" + isIntercept);
        if (intercept) {
            if (!isIntercept) {
                ev.setAction(MotionEvent.ACTION_DOWN);
                isIntercept = true;
            }
        } else {
            if (isIntercept) {
                ev.setAction(MotionEvent.ACTION_DOWN);
                isIntercept = false;
            }
        }
    }

    private void resetTouch() {
        isIntercept = true;
        lastX = 0;
        lastY = 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(this.getClass().getName(),"onInterceptTouchEvent ev:"+ev.getAction());
        if (!isIntercept) {
            return false;
        }
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (!isIntercept) {
                    return true;
                }
        }
        return super.onTouchEvent(ev);
    }


    public interface OnScrollViewTouchListener {

        public int getTabStripTopHeight(MotionEvent event);

        public boolean isSlidingTop(MotionEvent event);

    }


}
