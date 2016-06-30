package com.frogoutofwell.yullfrogapplication.home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.DoDetail;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class DoBestViewHolder extends RecyclerView.ViewHolder {
    ImageView logoImgView;
    TextView nameView, rateView, classView, commentView, commentGoodView,commentBadView;

    DoDetail doDetail;

    public interface OnDetailClickListener{
        public void onItemClick(View view, int seq);
    }

    OnDetailClickListener mListener;
    public void setOnDetailClickListener(OnDetailClickListener listener) {
        mListener = listener;
    }

    public DoBestViewHolder(View itemView) {
        super(itemView);
        logoImgView = (ImageView) itemView.findViewById(R.id.img_logo);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        rateView = (TextView)itemView.findViewById(R.id.text_rate);
        classView = (TextView)itemView.findViewById(R.id.text_info);
        commentView = (TextView)itemView.findViewById(R.id.text_comment);
        commentGoodView = (TextView)itemView.findViewById(R.id.text_commendgood);
        commentBadView = (TextView)itemView.findViewById(R.id.text_commentbad);

        Button btn_detail = (Button)itemView.findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("button","버트느느느느느 눌렷다 "+doDetail.getSeq());
                if (mListener != null) {
                    mListener.onItemClick(v,doDetail.getSeq());
                }
            }
        });
    }

    public void setDoBest(DoDetail doDetail){
        this.doDetail = doDetail;
        Glide.with(logoImgView.getContext()).load(doDetail.getCompanyLogo()).into(logoImgView);
      //  Log.i("logoglgoglgo","home logogogogogog"+doDetail.getCompanyLogo());
        nameView.setText(doDetail.getActivityName());
        rateView.setText(""+doDetail.getRate());
        classView.setText(doDetail.getCompanyName());
        commentView.setText(" "+doDetail.getComment()+"");
        commentGoodView.setText(doDetail.getCommentGood());
        commentBadView.setText(doDetail.getCommentBad());
    }

}
