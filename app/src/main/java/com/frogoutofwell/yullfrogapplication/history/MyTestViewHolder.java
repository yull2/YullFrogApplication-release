package com.frogoutofwell.yullfrogapplication.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class MyTestViewHolder extends RecyclerView.ViewHolder {

    ImageView logoView;
    TextView writeDateView, classView, termView, questionView, levelView, resultView;

    TestDetail testDetail;

    String[] level, result;

    public interface OnItemClickListener {
        public void onItemClick(View view, TestDetail testDetail);
    }

    OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyTestViewHolder(View itemView) {
        super(itemView);
        level = itemView.getContext().getResources().getStringArray(R.array.test_level);
        result = itemView.getContext().getResources().getStringArray(R.array.test_result);

        logoView = (ImageView)itemView.findViewById(R.id.img_logo);
        writeDateView = (TextView)itemView.findViewById(R.id.text_writedate);
        classView = (TextView)itemView.findViewById(R.id.text_classinfo);
        termView = (TextView)itemView.findViewById(R.id.text_term);
        questionView = (TextView)itemView.findViewById(R.id.text_commendgood);
        levelView = (TextView)itemView.findViewById(R.id.text_level);
        resultView = (TextView)itemView.findViewById(R.id.text_info);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v,testDetail);
                }
            }
        });
    }

    public void setMyTest(TestDetail testDetail){
        this.testDetail = testDetail;
        Glide.with(logoView.getContext()).load(testDetail.getCompanyLogo()).into(logoView);
        writeDateView.setText(testDetail.getWriteDate());
        classView.setText(testDetail.getActivityName());
        termView.setText(testDetail.getTerm());
        questionView.setText(testDetail.getQuestion());
        levelView.setText(level[testDetail.getLevel()]+"");
        resultView.setText(result[testDetail.getResult()]+"");

    }

}
