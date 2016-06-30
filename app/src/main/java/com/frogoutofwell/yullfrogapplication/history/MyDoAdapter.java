package com.frogoutofwell.yullfrogapplication.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.DoDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class MyDoAdapter extends RecyclerView.Adapter<MyDoViewHolder> {

    List<DoDetail> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(DoDetail doDetail) {
        items.add(doDetail);
        notifyDataSetChanged();
    }

    public void addAll(List<DoDetail> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    MyDoViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MyDoViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public MyDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_do_history, parent, false);
        return new MyDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyDoViewHolder holder, int position) {
        holder.setMyDo(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
