package com.frogoutofwell.yullfrogapplication.inter;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;

import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActClassFragment extends DialogFragment {

   // final String[] actClass = new String[]{"전체","해외탐방","국내봉사","해외봉사","강연","멘토링","서포터즈","마케터","홍보대사","기자단","기획단","기타"};

    String title;
    String[] catecory;

    public static ActClassFragment newInstance(String title, String[] catecory){
        ActClassFragment f = new ActClassFragment();
        Bundle b = new Bundle();
        b.putString("title", title);
        b.putStringArray("key", catecory);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            title = getArguments().getString("title");
            catecory = getArguments().getStringArray("key");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setSingleChoiceItems(catecory, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getContext(),"select value : "+ catecory[which],Toast.LENGTH_SHORT).show();

                Fragment select = getFragmentManager().findFragmentByTag("act");
                if (select != null) dialog.dismiss();;
            }
        });
        return builder.create();
    }

}
