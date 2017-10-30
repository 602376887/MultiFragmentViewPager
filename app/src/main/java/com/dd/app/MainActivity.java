package com.dd.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.dd.viewpager.CustomHeightViewPager;
import com.dd.viewpager.NewCustomScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//ScrolleView嵌套ViewPager 解决切换pager的item时候会自动置顶 ScrollView下的layout需要descendantFocusability=blocksDescendants
public class MainActivity extends FragmentActivity implements NewCustomScrollView.OnScrollViewTouchListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tltle1)
    TextView tltle1;
    @BindView(R.id.tltle2)
    TextView tltle2;
    @BindView(R.id.tltle3)
    TextView tltle3;
    @BindView(R.id.vp)
    CustomHeightViewPager vp;
    @BindView(R.id.scroll)
    NewCustomScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(scroll.getScrollY() < getTabStripTopHeight()){
                    ((PagerFragment)((MyPagerAdapter)vp.getAdapter()).getItem(vp.getCurrentItem())).lvPager1
                            .smoothScrollToPositionFromTop(0,0,0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        scroll.setOnTouchlistener(this);
    }

    @OnClick({R.id.tltle1, R.id.tltle2, R.id.tltle3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tltle1:
                vp.setCurrentItem(0,false);
                break;
            case R.id.tltle2:
                vp.setCurrentItem(1,false);
                break;
            case R.id.tltle3:
                vp.setCurrentItem(2,false);
                break;
        }
    }

    @Override
    public int getTabStripTopHeight() {
            return findViewById(R.id.iv_head).getMeasuredHeight();
    }

    @Override
    public boolean isSlidingTop() {
        return ((PagerFragment)((MyPagerAdapter)vp.getAdapter()).getItem(vp.getCurrentItem())).isTop();
    }


}
