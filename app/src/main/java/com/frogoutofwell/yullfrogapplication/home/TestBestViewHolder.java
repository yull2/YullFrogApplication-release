package com.frogoutofwell.yullfrogapplication.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class TestBestViewHolder extends RecyclerView.ViewHolder {
    ImageView logoImgView;
    TextView nameView, levelView, resultView ,questionView, answerView;

    TestDetail testDetail;

    String[] level, result;

    public interface OnDetailClickListener{
        public void onItemClick(View view, int seq);
    }

    OnDetailClickListener mListener;
    public void setOnDetailClickListener(OnDetailClickListener listener) {
        mListener = listener;
    }


    public TestBestViewHolder(View itemView) {
        super(itemView);
        level = itemView.getContext().getResources().getStringArray(R.array.test_level);
        result = itemView.getContext().getResources().getStringArray(R.array.test_result);

        logoImgView = (ImageView) itemView.findViewById(R.id.img_logo);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        levelView = (TextView)itemView.findViewById(R.id.text_level);
        resultView = (TextView)itemView.findViewById(R.id.text_info);
        questionView = (TextView)itemView.findViewById(R.id.text_commendgood);
        answerView = (TextView)itemView.findViewById(R.id.text_answer);

        Button btn_detail = (Button)itemView.findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("button","버트느느느느느 눌렷다 "+testDetail.getSeq());
                if (mListener != null) {
                    mListener.onItemClick(v,testDetail.getSeq());
                }
            }
        });
    }

    public void setTestBest(TestDetail testDetail){
        this.testDetail = testDetail;
        nameView.setText(testDetail.getActivityName());
        levelView.setText(level[testDetail.getLevel()]);
        resultView.setText(result[testDetail.getResult()]);
        questionView.setText(testDetail.getQuestion());
        answerView.setText(testDetail.getAnswer());
    }


}
