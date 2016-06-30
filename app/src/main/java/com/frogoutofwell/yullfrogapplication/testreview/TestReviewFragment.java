package com.frogoutofwell.yullfrogapplication.testreview;


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
import com.frogoutofwell.yullfrogapplication.data.InterTestReviewResult;
import com.frogoutofwell.yullfrogapplication.data.PointCheckResult;
import com.frogoutofwell.yullfrogapplication.data.TestDetail;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.write.WriteTestActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestReviewFragment extends Fragment {

    private static final String ARG_NAME = "param1";
    private String mName;

    int seq, detailSeq;

    RecyclerView listView;
    TextView countView;
    TestReviewAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public TestReviewFragment() {
        // Required empty public constructor
    }

    public static TestReviewFragment newInstance(String name){
        TestReviewFragment fragment = new TestReviewFragment();
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
       // Log.i("testreviewf","seeeeeeeeeeeq : "+seq );

        mAdapter = new TestReviewAdapter();
        mAdapter.setOnItemClickListener(new TestFirstViewHolder.OnFirstItemClickListener() {
            @Override
            public void onItemClick(View view, int seq) {
                Intent intent = new Intent(getContext(), TestReviewDetailActivity.class);
                intent.putExtra("detailSeq",seq);
                startActivity(intent);
            }
        });
        mAdapter.setOnItemClickListener(new TestSecondViewHolder.OnSecondItemClickListener() {
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
        View view = inflater.inflate(R.layout.fragment_test_review, container, false);
        countView = (TextView)view.findViewById(R.id.text_count);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

     /*   Button btn = (Button)view.findViewById(R.id.btn_go);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteTestActivity.class);
                startActivity(intent);
            }
        });*/
        return view;
    }


    private void setData() {
        NetworkManager.getInstance().getFrogInterTestReview(getContext(), seq, new NetworkManager.OnResultListener<InterTestReviewResult>() {
            @Override
            public void onSuccess(Request request, InterTestReviewResult result) {
                mAdapter.clear();
                countView.setText(""+result.totalInterCount);
                mAdapter.setLevelImage(result.totalInterLevel);
                mAdapter.addAll(result.testDetails.testDetails);
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
        NetworkManager.getInstance().getMyPointCheck(getContext(),detailSeq, new NetworkManager.OnResultListener<PointCheckResult>() {
            @Override
            public void onSuccess(Request request, PointCheckResult result) {
                if (result.status.equals("OK")){
                    Intent intent = new Intent(getContext(), TestReviewDetailActivity.class);
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
