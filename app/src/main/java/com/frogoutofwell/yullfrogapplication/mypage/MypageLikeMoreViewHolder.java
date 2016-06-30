package com.frogoutofwell.yullfrogapplication.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.frogoutofwell.yullfrogapplication.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class MypageLikeMoreViewHolder extends RecyclerView.ViewHolder {

    ImageView moreView;


    public interface OnItemClickListener {
        public void onItemClick(View view);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MypageLikeMoreViewHolder(final View itemView) {
        super(itemView);
        moreView = (ImageView)itemView.findViewById(R.id.img_likemore);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(v);
                }
            }
        });
    }

    public  void setLikeMore(){

    }
}
