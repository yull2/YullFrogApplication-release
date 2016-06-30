package com.frogoutofwell.yullfrogapplication.inter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frogoutofwell.yullfrogapplication.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class CategoryDialogAdapter extends RecyclerView.Adapter<CategoryDialogViewHolder> {

    String[] items;

    public void addAll(String[] items) {
        this.items = items;
        notifyDataSetChanged();
    }

    CategoryDialogViewHolder.OnItemClickListener mListener;
    public void setOnClickListener(CategoryDialogViewHolder.OnItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public CategoryDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_dialog_item, parent, false);
        return new CategoryDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryDialogViewHolder holder, int position) {
        holder.setDialogItem(items[position]);
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
