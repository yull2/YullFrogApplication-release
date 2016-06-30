package com.frogoutofwell.yullfrogapplication.mypage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.InterMainActivity;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetail;
import com.frogoutofwell.yullfrogapplication.data.MainInterResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class LikeMoreDetailActivity extends AppCompatActivity {

    RecyclerView listView;
    LikeMoreAdapter mAdapter;
    GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("찜 대외활동");
        setContentView(R.layout.activity_like_more_detail);

        mAdapter = new LikeMoreAdapter();
        mAdapter.setOnItemClickListener(new LikeMoreViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ActivityDetail activityDetail) {
                Intent intent = new Intent(LikeMoreDetailActivity.this, InterMainActivity.class);
                intent.putExtra("seq",activityDetail.getSeq());
                startActivity(intent);
            }
        });
        listView = (RecyclerView)findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(this, 2);
        listView.setLayoutManager(mLayoutManager);

    }

    private void setData() {
        NetworkManager.getInstance().getMypageLikeItem(this, new NetworkManager.OnResultListener<MainInterResult>() {
            @Override
            public void onSuccess(Request request, MainInterResult result) {
                mAdapter.clear();
                mAdapter.addAll(result.activityDetails.activityDetails);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(LikeMoreDetailActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setData();
    }
}
