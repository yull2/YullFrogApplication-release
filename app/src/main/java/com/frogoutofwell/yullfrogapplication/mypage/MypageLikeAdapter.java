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
 * Created by Tacademy on 2016-05-18.
 */
public class MypageLikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ActivityDetail> items = new ArrayList<>();

    public static final int VIEW_TYPE_LIKE_ITEM = 1;
    public static final int VIEW_TYPE_ITEM_MORE = 2;

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void setLikeItem(List<ActivityDetail> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void setLikeMoreButton(){

    }

    MypageLikeMoreViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MypageLikeMoreViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position <= items.size()-1) return VIEW_TYPE_LIKE_ITEM;
        if (position == items.size()) return VIEW_TYPE_ITEM_MORE;

        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_LIKE_ITEM: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_like_doitem, parent, false);
                return new MypageLikeViewHolder(view);
            }
            case VIEW_TYPE_ITEM_MORE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_like_item_more, parent, false);
                return new MypageLikeMoreViewHolder(view);
            }
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= 0 && position <= items.size()-1){
            MypageLikeViewHolder h = (MypageLikeViewHolder)holder;
            h.setLikeItem(items.get(position));
            return;
        }
        if (position == items.size()){
            MypageLikeMoreViewHolder h = (MypageLikeMoreViewHolder)holder;
            h.setOnItemClickListener(mListener);
            return;
        }

    }

    @Override
    public int getItemCount() {
        if (items.size()>0) {
            return items.size()+1;
        }else return items.size();
    }
}
