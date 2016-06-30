package com.frogoutofwell.yullfrogapplication.account;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.frogoutofwell.yullfrogapplication.R;

/**
 * Created by Tacademy on 2016-06-01.
 */
public class IndusViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    String str;
    int position;

    public IndusViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.text_indus);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, str, position);
                }
            }
        });
    }

    public void setIndus(String str, int postion){
        this.str = str;
        this.position = postion;
        textView.setText(str);
    }

    boolean isChecked;
    public void setChecked(boolean checked){
        if (isChecked != checked){
            isChecked = checked;
            drawCheck();
        }
    }

    public void drawCheck(){
        if (isChecked){
            //Log.i("actclass","truetrue");
            textView.setTextColor(Color.parseColor("#454545"));
            textView.setBackgroundColor(Color.parseColor("#bbdefb"));
        }else {
            // Log.i("actclass","falsefalse");
            textView.setTextColor(Color.parseColor("#707070"));
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, String str, int postion);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


}
