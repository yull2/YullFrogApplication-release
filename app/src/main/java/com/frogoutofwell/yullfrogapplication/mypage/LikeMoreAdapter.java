package com.frogoutofwell.yullfrogapplication.mypage;

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
 * Created by Tacademy on 2016-05-23.
 */
public class LikeMoreAdapter extends RecyclerView.Adapter<LikeMoreViewHolder> {

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

    LikeMoreViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(LikeMoreViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public LikeMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recommend_doitem, parent, false);
        return new LikeMoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LikeMoreViewHolder holder, int position) {
        holder.setInterThumb(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
