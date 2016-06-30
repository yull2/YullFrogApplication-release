package com.frogoutofwell.yullfrogapplication.testreview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class TestReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_TEST_LEVEL_IMAGE = 0;
    public static final int VIEW_TYPE_TEST_FIRSTREVIEW = 1;
    public static final int VIEW_TYPE_TEST_SECONDREVIEW = 2;

    int levelSrc;
    List<TestDetail> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void setLevelImage(int level){
        levelSrc = level;
        notifyDataSetChanged();
    }

    public void add(TestDetail testDetail) {
        items.add(testDetail);
        notifyDataSetChanged();
    }

    public void addAll(List<TestDetail> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    TestFirstViewHolder.OnFirstItemClickListener mfListener;
    public void setOnItemClickListener(TestFirstViewHolder.OnFirstItemClickListener listener){
        mfListener = listener;
    }

    TestSecondViewHolder.OnSecondItemClickListener msListener;
    public void setOnItemClickListener(TestSecondViewHolder.OnSecondItemClickListener listener){
        msListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_TEST_LEVEL_IMAGE;
        if (position == 1) return VIEW_TYPE_TEST_FIRSTREVIEW;
        if (position > 1) return VIEW_TYPE_TEST_SECONDREVIEW;
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TEST_LEVEL_IMAGE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_inter_level_img, parent, false);
                return new TestLevelViewHolder(view);
            }
            case VIEW_TYPE_TEST_FIRSTREVIEW: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_test_review,  parent, false);
                return new TestFirstViewHolder(view);
            }
            case VIEW_TYPE_TEST_SECONDREVIEW: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_test_reviews,  parent, false);
                return new TestSecondViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0){
            TestLevelViewHolder h = (TestLevelViewHolder)holder;
            h.setLevelImage(levelSrc);
            return;
        }
        if (position == 1){
            TestFirstViewHolder h = (TestFirstViewHolder)holder;
            h.setTestFirst(items.get(position-1));
            h.setOnItemClickListener(mfListener);
            return;
        }
        if (position > 1 ){
            TestSecondViewHolder h = (TestSecondViewHolder)holder;
            h.setTestSecond(items.get(position-1));
            h.setOnItemClickListener(msListener);
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (items.size() > 0){
            return items.size()+1;
        }
        return items.size();
    }
}
