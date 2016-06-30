package com.frogoutofwell.yullfrogapplication.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.DoDetail;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class MyDoViewHolder extends RecyclerView.ViewHolder {

    ImageView logoView;
    TextView writeDateView, classView, termView, commentView, rateView;
    ImageView rateBar;

    DoDetail doDetail;

    public interface OnItemClickListener {
        public void onItemClick(View view, DoDetail doDetail);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyDoViewHolder(View itemView) {
        super(itemView);
        logoView = (ImageView)itemView.findViewById(R.id.img_logo);
        writeDateView = (TextView)itemView.findViewById(R.id.text_writedate);
        classView = (TextView)itemView.findViewById(R.id.text_classinfo);
        termView = (TextView)itemView.findViewById(R.id.text_term);
        commentView = (TextView)itemView.findViewById(R.id.text_comment);
        rateView = (TextView)itemView.findViewById(R.id.text_rate);
        rateBar = (ImageView) itemView.findViewById(R.id.img_rate);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,doDetail);
                }
            }
        });
    }

    public void setMyDo(DoDetail doDetail){
        this.doDetail = doDetail;
        Glide.with(logoView.getContext()).load(doDetail.getCompanyLogo()).into(logoView);
        writeDateView.setText(doDetail.getWriteDate());
        classView.setText(doDetail.getCompanyName());
        termView.setText(doDetail.getTerm());
        commentView.setText(doDetail.getComment());
        rateView.setText(doDetail.getRate() + " ");


        if (doDetail.getRate() == 0){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star0);
        }else if (doDetail.getRate() == 0.5){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star0_1);
        }else if (doDetail.getRate() == 1){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star1);
        }else if (doDetail.getRate() == 1.5){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star1_1);
        }else if (doDetail.getRate() == 2){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star2);
        }else if (doDetail.getRate() == 2.5){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star2_1);
        }else if (doDetail.getRate() == 3){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star3);
        }else if (doDetail.getRate() == 3.5){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star3_1);
        }else if (doDetail.getRate() == 4){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star4);
        }else if (doDetail.getRate() == 4.5){
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star4_1);
        }else {
            rateBar.setImageResource(R.drawable.activityreview_detail_ic_star5);
        }

    }

}
