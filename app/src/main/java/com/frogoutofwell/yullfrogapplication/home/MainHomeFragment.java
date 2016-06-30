package com.frogoutofwell.yullfrogapplication.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.InterMainActivity;
import com.frogoutofwell.yullfrogapplication.MyApplication;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetail;
import com.frogoutofwell.yullfrogapplication.data.DoDetail;

import com.frogoutofwell.yullfrogapplication.data.MainHomeDetailResult;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;
import com.frogoutofwell.yullfrogapplication.doreview.DoReviewDetailActivity;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.testreview.TestReviewDetailActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {

    private static final String ARG_NAME = "param1";
    private String mName;

    RecyclerView listView;
    MainHomeAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public MainHomeFragment() {
        // Required empty public constructor
    }

    public static MainHomeFragment newInstance(String name){
        MainHomeFragment fragment = new MainHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
        }
        mAdapter = new MainHomeAdapter();
        mAdapter.setOnItemClickListener(new ImageViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int seq) {
                Intent intent = new Intent(getContext(), InterMainActivity.class);
                intent.putExtra("seq",seq);
                startActivity(intent);
            }
        });
        mAdapter.setOnDetailClickListener(new DoBestViewHolder.OnDetailClickListener() {
            @Override
            public void onItemClick(View view, int seq) {
                //Log.i("btn","djafkldjlkfa벝ㄴ 22222");
                Intent intent = new Intent(getContext(), DoReviewDetailActivity.class);
                intent.putExtra("detailSeq", seq);
                startActivity(intent);
            }
        });
        mAdapter.setOnDetailClickListener(new TestBestViewHolder.OnDetailClickListener() {
            @Override
            public void onItemClick(View view, int seq) {
                //Log.i("btn","djafkldjlkfa벝ㄴ 22222");
                Intent intent = new Intent(getContext(), TestReviewDetailActivity.class);
                intent.putExtra("detailSeq", seq);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        setData();
        return view;
    }

    private void setData() {
        NetworkManager.getInstance().getFrogMainHomeFeed(getContext(), new NetworkManager.OnResultListener<MainHomeDetailResult>() {
            @Override
            public void onSuccess(Request request, MainHomeDetailResult result) {

                mAdapter.setDoDetail(result.home.doDetail);
                mAdapter.setTestDetail(result.home.testDetail);
                mAdapter.setActivityImg(result.home.activityDetails.activityDetails);

            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
