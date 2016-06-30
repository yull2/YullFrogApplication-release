package com.frogoutofwell.yullfrogapplication.history;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.MyTestReviewResult;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.testreview.TestReviewDetailActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTestHistoryFragment extends Fragment {

    private static final String ARG_NAME = "param1";
    private String mName;

    TextView countView;
    RecyclerView listView;
    MyTestAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public MyTestHistoryFragment() {
        // Required empty public constructor
    }

    public static MyTestHistoryFragment newInstance(String name){
        MyTestHistoryFragment fragment = new MyTestHistoryFragment();
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
        mAdapter = new MyTestAdapter();
        mAdapter.setOnItemClickListener(new MyTestViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, TestDetail testDetail) {
                Intent intent = new Intent(getContext(), TestReviewDetailActivity.class);
                intent.putExtra("detailSeq",testDetail.getSeq());
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_test_history, container, false);
        countView = (TextView)view.findViewById(R.id.text_count);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

        setData();

        return view;
    }

    private void setData() {
        NetworkManager.getInstance().getMyTestReview(getContext(), new NetworkManager.OnResultListener<MyTestReviewResult>() {
            @Override
            public void onSuccess(Request request, MyTestReviewResult result) {
                mAdapter.clear();
                countView.setText(result.totalInterCount+"");
                mAdapter.addAll(result.testDetails.testDetails);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
