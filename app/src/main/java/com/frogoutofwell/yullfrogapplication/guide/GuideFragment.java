package com.frogoutofwell.yullfrogapplication.guide;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetailResult;

import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {

    private static final String ARG_NAME = "param1";
    private String mName;

    int seq;

    ImageView guideView;

    public GuideFragment() {
        // Required empty public constructor
    }

    public static GuideFragment newInstance(String name){
        GuideFragment fragment = new GuideFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_guide, container, false);
        guideView = (ImageView)view.findViewById(R.id.img_logo);
        setData();
        return view;
    }

    private void setData(){
       NetworkManager.getInstance().getFrogInterGuide(getContext(), seq,new NetworkManager.OnResultListener<ActivityDetailResult>(){

            @Override
            public void onSuccess(Request request, ActivityDetailResult result) {
                String srcImg = result.activityDetail.guideImg;
               // Log.i("Guide Image Url", "Guide Image Urllllllllllllllllllll :"+srcImg);
                Glide.with(guideView.getContext()).load(srcImg).into(guideView);
       }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
