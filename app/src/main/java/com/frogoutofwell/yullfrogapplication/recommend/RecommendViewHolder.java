package com.frogoutofwell.yullfrogapplication.recommend;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetail;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class RecommendViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, companyView, classView, duedateView, rateView;

    ActivityDetail activityDetail;

    public interface OnItemClickListener {
        public void onItemClick(View view, ActivityDetail activityDetail);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecommendViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.img_logo);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        companyView = (TextView)itemView.findViewById(R.id.text_company);
        classView = (TextView)itemView.findViewById(R.id.text_info);
        duedateView = (TextView)itemView.findViewById(R.id.text_due);
        rateView = (TextView)itemView.findViewById(R.id.text_rate);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,activityDetail);
                }
            }
        });
    }

    public void setInterThumb(ActivityDetail activityDetail){

        this.activityDetail = activityDetail;
        Glide.with(imageView.getContext()).load(activityDetail.getGuideImg()).into(imageView);
        nameView.setText(activityDetail.getName());
        companyView.setText(activityDetail.getCompanyName());
        classView.setText(activityDetail.getActClass());
        duedateView.setText(activityDetail.getEndDate());
        rateView.setText(activityDetail.getAverageRate()+" ");

    }

}
