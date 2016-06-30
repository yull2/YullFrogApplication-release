package com.frogoutofwell.yullfrogapplication.history;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class MyHistoryPagerAdapter extends FragmentPagerAdapter {

    public MyHistoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return MyTestHistoryFragment.newInstance("args:"+position);
        }
        return MyDoHistoryFragment.newInstance("args:"+position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
