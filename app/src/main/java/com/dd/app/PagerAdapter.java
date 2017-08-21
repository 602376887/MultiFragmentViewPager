package com.dd.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dengdun on 2017/8/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    PagerFragment pagerFragment1;
    PagerFragment pagerFragment2;
    PagerFragment pagerFragment3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if(pagerFragment1 == null){
                    pagerFragment1 = new PagerFragment();
                }
                return pagerFragment1;
            case 1:
                if(pagerFragment2 == null){
                    pagerFragment2 = new PagerFragment();
                }
                return pagerFragment2;
            case 2:
                if(pagerFragment3 == null){
                    pagerFragment3 = new PagerFragment();
                }
                return pagerFragment3;
        }
        return new PagerFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
