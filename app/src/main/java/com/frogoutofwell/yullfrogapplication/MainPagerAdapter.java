package com.frogoutofwell.yullfrogapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.frogoutofwell.yullfrogapplication.home.MainHomeFragment;
import com.frogoutofwell.yullfrogapplication.inter.MainInterFragment;
import com.frogoutofwell.yullfrogapplication.mypage.MainMypageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class MainPagerAdapter extends FragmentPagerAdapter{

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return MainHomeFragment.newInstance("args:"+position);
        } else if(position == 1){
            return MainInterFragment.newInstance("args:"+position);
        }
        return MainMypageFragment.newInstance("args:"+position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TAB"+position;
    }
}
