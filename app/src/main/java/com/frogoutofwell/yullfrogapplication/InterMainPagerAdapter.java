package com.frogoutofwell.yullfrogapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.frogoutofwell.yullfrogapplication.doreview.DoReviewFragment;
import com.frogoutofwell.yullfrogapplication.guide.GuideFragment;
import com.frogoutofwell.yullfrogapplication.recommend.RecommendFragment;
import com.frogoutofwell.yullfrogapplication.testreview.TestReviewFragment;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class InterMainPagerAdapter extends FragmentPagerAdapter {

    public InterMainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return GuideFragment.newInstance("args:"+position);
        }else if (position == 1){
            return TestReviewFragment.newInstance("args:"+position);
        }else if (position == 2){
            return DoReviewFragment.newInstance("args:"+position);
        }
        return RecommendFragment.newInstance("args:"+position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
