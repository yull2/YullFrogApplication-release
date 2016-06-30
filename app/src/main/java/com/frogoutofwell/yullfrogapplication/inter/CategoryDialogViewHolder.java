package com.frogoutofwell.yullfrogapplication.inter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.frogoutofwell.yullfrogapplication.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class CategoryDialogViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    String name;

    public interface OnItemClickListener {
         public void onItemClick(View view, String name);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CategoryDialogViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.text_dialogitem);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onItemClick(v,name);
                }
            }
        });
    }

    public void setDialogItem(String item){
        this.name = item;
        textView.setText(item);
    }
}
