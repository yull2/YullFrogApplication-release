package com.frogoutofwell.yullfrogapplication.doreview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.DoDetail;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class DoFirstViewHolder extends RecyclerView.ViewHolder {

    TextView rateView, commentView, commentGoodView, commentBadView;
    ImageView rateBar;

    DoDetail doDetail;

    public interface OnFirstItemClickListener {
        public void onItemClick(View view, int seq);
    }

    OnFirstItemClickListener mListener;
    public void setOnItemClickListener(OnFirstItemClickListener listener) {
        mListener = listener;
    }

    public DoFirstViewHolder(View itemView) {
        super(itemView);
        rateBar = (ImageView)itemView.findViewById(R.id.img_rate);
        rateView = (TextView)itemView.findViewById(R.id.text_rate);
        commentView = (TextView)itemView.findViewById(R.id.text_comment);
        commentGoodView = (TextView)itemView.findViewById(R.id.text_commendgood);
        commentBadView = (TextView)itemView.findViewById(R.id.text_commentbad);
        Button btn_detail = (Button)itemView.findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,doDetail.getSeq());
                }
            }
        });

    }

    public void setDoFirst(DoDetail doDetail){
        this.doDetail = doDetail;
        rateView.setText(doDetail.getRate()+" ");
        commentView.setText(doDetail.getComment());
        commentGoodView.setText(doDetail.getCommentGood());
        commentBadView.setText(doDetail.getCommentBad());

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
