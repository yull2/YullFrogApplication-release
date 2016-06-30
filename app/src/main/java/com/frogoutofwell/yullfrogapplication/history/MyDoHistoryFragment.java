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
import com.frogoutofwell.yullfrogapplication.data.DoDetail;
import com.frogoutofwell.yullfrogapplication.data.MyDoReviewResult;
import com.frogoutofwell.yullfrogapplication.doreview.DoReviewDetailActivity;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDoHistoryFragment extends Fragment {

    private static final String ARG_NAME = "param1";
    private String mName;

    TextView countView;
    RecyclerView listView;
    MyDoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public MyDoHistoryFragment() {
        // Required empty public constructor
    }

    public static MyDoHistoryFragment newInstance(String name){
        MyDoHistoryFragment fragment = new MyDoHistoryFragment();
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
        mAdapter = new MyDoAdapter();
        mAdapter.setOnItemClickListener(new MyDoViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, DoDetail doDetail) {
                Intent intent = new Intent(getContext(), DoReviewDetailActivity.class);
                intent.putExtra("detailSeq",doDetail.getSeq());
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_do_history, container, false);

        countView = (TextView)view.findViewById(R.id.text_count);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);
        setData();

        return view;
    }

    private void setData() {
        NetworkManager.getInstance().getMyDoReview(getContext(), new NetworkManager.OnResultListener<MyDoReviewResult>() {
            @Override
            public void onSuccess(Request request, MyDoReviewResult result) {
                mAdapter.clear();
                countView.setText(result.totalPostCount+"");
                mAdapter.addAll(result.doDetails.doDetail);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
