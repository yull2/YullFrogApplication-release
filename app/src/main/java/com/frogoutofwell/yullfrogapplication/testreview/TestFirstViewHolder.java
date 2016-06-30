package com.frogoutofwell.yullfrogapplication.testreview;


import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;

import java.util.Arrays;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class TestFirstViewHolder extends RecyclerView.ViewHolder {

    TextView classInfoView, levelView, resultView, questionView, answerView;

    TestDetail testDetail;

    String[] level, result;

    public interface OnFirstItemClickListener {
        public void onItemClick(View view, int seq);
    }

    OnFirstItemClickListener mListener;
    public void setOnItemClickListener(OnFirstItemClickListener listener) {
        mListener = listener;
    }

    public TestFirstViewHolder(View itemView) {
        super(itemView);
        level = itemView.getContext().getResources().getStringArray(R.array.test_level);
        result = itemView.getContext().getResources().getStringArray(R.array.test_result);

        classInfoView = (TextView)itemView.findViewById(R.id.text_classinfo);
        levelView = (TextView)itemView.findViewById(R.id.text_level);
        resultView = (TextView)itemView.findViewById(R.id.text_result);
        questionView = (TextView)itemView.findViewById(R.id.text_commendgood);
        answerView = (TextView)itemView.findViewById(R.id.text_answer);
        Button btn_detail = (Button)itemView.findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,testDetail.getSeq());
                }
            }
        });

    }

    public void setTestFirst(TestDetail testDetail){
        this.testDetail = testDetail;
        classInfoView.setText(testDetail.getTerm());
        levelView.setText(level[testDetail.getLevel()]);
        resultView.setText(result[testDetail.getResult()]);
        questionView.setText(testDetail.getQuestion());
        answerView.setText(testDetail.getAnswer());

    }

}
