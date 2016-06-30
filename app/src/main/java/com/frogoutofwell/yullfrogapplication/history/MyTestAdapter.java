package com.frogoutofwell.yullfrogapplication.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class MyTestAdapter extends RecyclerView.Adapter<MyTestViewHolder> {

    List<TestDetail> items = new ArrayList<>();

    public void clear() {
        items.clear();
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

    MyTestViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MyTestViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public MyTestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_test_history, parent, false);
        return new MyTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyTestViewHolder holder, int position) {
        holder.setMyTest(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
