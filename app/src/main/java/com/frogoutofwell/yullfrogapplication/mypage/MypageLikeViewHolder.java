package com.frogoutofwell.yullfrogapplication.mypage;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetail;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class MypageLikeViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, classView;

    ActivityDetail activityDetail;

    public MypageLikeViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.img_logo);
        nameView = (TextView) itemView.findViewById(R.id.text_name);
        classView = (TextView) itemView.findViewById(R.id.text_info);
    }

    public void setLikeItem(ActivityDetail activityDetail) {
        this.activityDetail = activityDetail;
        Glide.with(imageView.getContext()).load(activityDetail.getGuideImg()).into(imageView);
        nameView.setText(activityDetail.getName());
        classView.setText(activityDetail.getActClass());
    }
}