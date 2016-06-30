package com.frogoutofwell.yullfrogapplication.inter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterDialogFragment extends DialogFragment {

    String title;
    String[] catecory;

    TextView titleView;
    RecyclerView listView;
    CategoryDialogAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public static InterDialogFragment newInstance(String title, String[] catecory){
        InterDialogFragment f = new InterDialogFragment();
        Bundle b = new Bundle();
        b.putString("title", title);
        b.putStringArray("key", catecory);
        f.setArguments(b);
        return f;
    }


    public interface OnItemSelectListener {
        public void onItemSelect(String item);
    }

    OnItemSelectListener mListener;
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        mListener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            title = getArguments().getString("title");
            catecory = getArguments().getStringArray("key");
        }
        mAdapter = new CategoryDialogAdapter();
        mAdapter.setOnClickListener(new CategoryDialogViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String name) {
               // Toast.makeText(getContext(),"select value : "+name,Toast.LENGTH_LONG).show();
                if (mListener != null) {
                    mListener.onItemSelect(name);
                }
                dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inter_dialog, container, false);
        listView = (RecyclerView) view.findViewById(R.id.dialog_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(mLayoutManager);

        setDialog();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog d = getDialog();
        d.setTitle(title);

    }

    private void setDialog(){
        mAdapter.addAll(catecory);
    }


}
