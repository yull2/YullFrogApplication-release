package com.frogoutofwell.yullfrogapplication.account;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frogoutofwell.yullfrogapplication.R;

/**
 * Created by Tacademy on 2016-06-01.
 */
public class ActclassChangeAdapter extends RecyclerView.Adapter<ActclassViewHolder> implements ActclassViewHolder.OnItemClickListener{

    String[] items;
    SparseBooleanArray checkedItems = new SparseBooleanArray();

    public void clear() {
        items = null;
        notifyDataSetChanged();
    }

    public void addAll(String[] items) {
        this.items = items;
        notifyDataSetChanged();
    }

    ActclassViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(ActclassViewHolder.OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public ActclassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_actclass_item, parent, false);
        return new ActclassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActclassViewHolder holder, int position) {
        holder.setActclass(items[position],position);
        holder.setOnItemClickListener(mListener);
        holder.setChecked(checkedItems.get(position));
    }

    @Override
    public int getItemCount() {
        return items.length;
    }


    @Override
    public void onItemClick(View view, String str, int postion) {
        boolean checked = !checkedItems.get(postion);
      // Log.i("actclassadapter","과연??? " + postion);
        checkedItems.put(postion,checked);
        notifyDataSetChanged();
    }

    public SparseBooleanArray getCheckedItems(){
        return checkedItems;
    }
}
