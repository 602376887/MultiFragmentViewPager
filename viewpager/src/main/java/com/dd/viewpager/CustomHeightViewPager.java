package com.dd.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

public class CustomHeightViewPager extends ViewPager {
    private final static String TAG = "CustomHeightViewPager";
    private int topHeight;
    private int height; //自定义高度

    public CustomHeightViewPager(Context context) {
        super(context);
        init();
        // height = ResolutionUtils.getScreenHeight(getContext()) - (int) ResolutionUtils.convertDpToPixel(CUSTOM_HEIGHT, getContext());
    }

    public CustomHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // height = ResolutionUtils.getScreenHeight(getContext()) - (int) ResolutionUtils.convertDpToPixel(CUSTOM_HEIGHT, getContext());
        // Log.d(TAG, "CustomHeightViewPager height = " + height);
    }


    private void init() {
        topHeight = getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar1_height) +
                getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar1_height);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (height <= 0) {
            height = getRootView().findViewById(android.R.id.content).getHeight() - topHeight;
            Log.d(TAG, "CustomListView-- height = " + height);
        }
        if (height != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}