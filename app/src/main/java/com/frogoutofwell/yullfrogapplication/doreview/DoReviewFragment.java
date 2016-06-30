package com.frogoutofwell.yullfrogapplication.doreview;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetail;
import com.frogoutofwell.yullfrogapplication.data.DoDetail;
import com.frogoutofwell.yullfrogapplication.data.InterDoReviewResult;
import com.frogoutofwell.yullfrogapplication.data.PointCheckResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.write.WriteDoActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoReviewFragment extends Fragment {

    private static final String ARG_NAME = "param1";
    private String mName;

    int seq, detailSeq;

    TextView countView;
    RecyclerView listView;
    DoReviewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public DoReviewFragment() {
        // Required empty public constructor
    }

    public static DoReviewFragment newInstance(String name){
        DoReviewFragment fragment = new DoReviewFragment();
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

        seq = getActivity().getIntent().getIntExtra("seq",1);

        mAdapter = new DoReviewAdapter();
        mAdapter.setOnItemClickListener(new DoFirstViewHolder.OnFirstItemClickListener() {
            @Override
            public void onItemClick(View view, int seq) {
                Intent intent = new Intent(getContext(), DoReviewDetailActivity.class);
                intent.putExtra("detailSeq",seq);
                startActivity(intent);
            }
        });
        mAdapter.setOnItemClickListener(new DoSecondViewHolder.OnSecondItemClickListener() {
            @Override
            public void onItemClick(View view, int seq) {
                detailSeq = seq;
                getPossible(detailSeq);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_do_review, container, false);

        countView = (TextView)view.findViewById(R.id.text_count);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

        return view;
    }

    private void setData() {
        NetworkManager.getInstance().getFrogInterDoReview(getContext(), seq, new NetworkManager.OnResultListener<InterDoReviewResult>() {
            @Override
            public void onSuccess(Request request, InterDoReviewResult result) {
                mAdapter.clear();
                countView.setText(result.totalPostCount+"");
               // Log.i("doreview","count : "+result.totalPostCountStar);
               /* mAdapter.setCountStar(result.totalPostCountStar);
                mAdapter.setTotalStar(result.averageRate);
                mAdapter.addAll(result.doDetails.doDetail);*/
                mAdapter.addAllItem(result);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void getPossible(int detailSeq){
        NetworkManager.getInstance().getMyPointCheck(getContext(), detailSeq, new NetworkManager.OnResultListener<PointCheckResult>() {
            @Override
            public void onSuccess(Request request, PointCheckResult result) {
                if (result.status.equals("OK")){
                    Intent intent = new Intent(getContext(), DoReviewDetailActivity.class);
                    intent.putExtra("detailSeq",result.seq);
                    startActivity(intent);
                }else {
                    //Toast.makeText(getContext(), "개굴이 부족합니다 : "+result.status,Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage("개굴이 부족합니다.");
                    alert.show();
                }

            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
