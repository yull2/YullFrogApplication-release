package com.frogoutofwell.yullfrogapplication.home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetail;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView1,imageView2,imageView3;
    ActivityDetail activityDetail1, activityDetail2;

    public interface OnItemClickListener {
        public void onItemClick(View view, int seq);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ImageViewHolder(View itemView) {
        super(itemView);
        imageView1 = (ImageView)itemView.findViewById(R.id.image_homemain);
        imageView2 = (ImageView)itemView.findViewById(R.id.image_homestart);
        imageView3 = (ImageView)itemView.findViewById(R.id.image_homeend);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,activityDetail1.getSeq());
                   // Log.i("imageviewholder","누가 눌렷게 +++ "+activityDetail1.getSeq());
                }
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,activityDetail2.getSeq());
                   // Log.i("imageviewholder","누가 눌렷게 +++ "+activityDetail2.getSeq());
                }
            }
        });
    }
   public void setHomeImg(List<ActivityDetail> activityDetails){
       this.activityDetail1 = activityDetails.get(0);
       this.activityDetail2 = activityDetails.get(1);
       Glide.with(imageView2.getContext()).load(activityDetail1.getGuideImg()).into(imageView2);
       Glide.with(imageView3.getContext()).load(activityDetail2.getGuideImg()).into(imageView3);
    }
}
