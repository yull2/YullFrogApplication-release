package com.frogoutofwell.yullfrogapplication.recommend;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetail;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendViewHolder> {

    List<ActivityDetail> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(ActivityDetail activityDetail) {
        items.add(activityDetail);
        notifyDataSetChanged();
    }

    public void addAll(List<ActivityDetail> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    RecommendViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(RecommendViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recommend_doitem, parent, false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.setInterThumb(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
