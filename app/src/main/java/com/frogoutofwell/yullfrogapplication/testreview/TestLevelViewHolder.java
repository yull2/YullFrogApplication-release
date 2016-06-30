package com.frogoutofwell.yullfrogapplication.testreview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class TestLevelViewHolder extends RecyclerView.ViewHolder{
    ImageView levelView;

    public TestLevelViewHolder(View itemView) {
        super(itemView);
        levelView = (ImageView)itemView.findViewById(R.id.img_level);
    }

    public void setLevelImage(int src){
        if (src==1) {
            levelView.setImageResource(R.drawable.ic_interviewbar01);
        }else if (src==2) {
            levelView.setImageResource(R.drawable.ic_interviewbar02);
        }else if (src==3) {
            levelView.setImageResource(R.drawable.ic_interviewbar03);
        }else if (src==4) {
            levelView.setImageResource(R.drawable.ic_interviewbar04);
        }else if (src==5) {
            levelView.setImageResource(R.drawable.ic_interviewbar05);
        }
    }
}
