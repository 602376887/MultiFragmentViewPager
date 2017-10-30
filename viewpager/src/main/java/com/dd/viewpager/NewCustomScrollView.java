package com.dd.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * Created by dengdun on 2017/8/17.
 */

public class NewCustomScrollView extends ScrollView {

    private static final String TAG = NewCustomScrollView.class.getSimpleName();

    private OnScrollViewTouchListener mOnScrollViewTouchListener;
    private boolean isIntercept;//是否拦截
    private float lastY;

    public NewCustomScrollView(Context context) {
        super(context);
    }

    public NewCustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnTouchlistener(OnScrollViewTouchListener onScrollViewTouchListener) {
        this.mOnScrollViewTouchListener = onScrollViewTouchListener;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        Log.i("setTouchEvent", " ev" + ev.getAction()+" y:"+y);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                cacluteIsNeedIntercept(ev);
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetTouch();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void cacluteIsNeedIntercept(MotionEvent ev){
        int tabTopHeight = mOnScrollViewTouchListener.getTabStripTopHeight();
        boolean isScrollDown = ev.getY() >= lastY;
        boolean isHeadVisiable = getScrollY() < tabTopHeight;
        if(!isScrollDown && getScrollY() == tabTopHeight && !isIntercept){
            ev.setAction(MotionEvent.ACTION_DOWN);
        }
        if(isScrollDown && getScrollY() == tabTopHeight && isSubScrollViewTop()){
            isIntercept = false;
            requestDisallowInterceptTouchEvent(false);
        }else {
            isIntercept = !isHeadVisiable;
            requestDisallowInterceptTouchEvent(!isHeadVisiable);
        }
    }

    private void resetTouch() {
        isIntercept = false;
        lastY = 0;
    }


    private boolean isSubScrollViewTop(){
        return mOnScrollViewTouchListener != null && mOnScrollViewTouchListener.isSlidingTop();
    }



    public interface OnScrollViewTouchListener {

        public int getTabStripTopHeight();//获取头部的高度

        public boolean isSlidingTop();//viewPager中的ListView是否滑动到顶部

    }


}
