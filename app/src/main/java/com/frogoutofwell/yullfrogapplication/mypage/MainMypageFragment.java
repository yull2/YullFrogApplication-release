package com.frogoutofwell.yullfrogapplication.mypage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.TypefaceManager;
import com.frogoutofwell.yullfrogapplication.account.AccountActivity;
import com.frogoutofwell.yullfrogapplication.data.MainMypageResult;
import com.frogoutofwell.yullfrogapplication.history.MyHistoryActivity;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.setting.SettingActivity;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainMypageFragment extends Fragment {

    private static final String ARG_NAME = "param1";
    private String mName;

    TextView pointView;
    RecyclerView listView;
    MypageLikeAdapter mAdapter;
    GridLayoutManager mLayoutManager;

    public MainMypageFragment() {
        // Required empty public constructor
    }

    public static MainMypageFragment newInstance(String name){
        MainMypageFragment fragment = new MainMypageFragment();
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
        mAdapter = new MypageLikeAdapter();
        mAdapter.setOnItemClickListener(new MypageLikeMoreViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(getContext(), LikeMoreDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_mypage, container, false);

        pointView = (TextView)view.findViewById(R.id.text_point);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setNestedScrollingEnabled(false);
        listView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        listView.setLayoutManager(mLayoutManager);

        Button btn_history = (Button)view.findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyHistoryActivity.class);
                startActivity(intent);
            }
        });

        Button btn_account = (Button)view.findViewById(R.id.btn_account);
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        Button btn_setting = (Button)view.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void setData() {
        NetworkManager.getInstance().getFrogMainMypage(getContext(), new NetworkManager.OnResultListener<MainMypageResult>() {
            @Override
            public void onSuccess(Request request, MainMypageResult result) {
                mAdapter.clear();
                pointView.setText(result.point+"개굴");
                mAdapter.setLikeItem(result.activityDetails.activityDetails);
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
}
