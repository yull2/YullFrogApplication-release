package com.frogoutofwell.yullfrogapplication.login;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    public SignupFragment() {
        // Required empty public constructor
    }

    EditText emailView, passwordView, passwordCheckView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        emailView = (EditText)view.findViewById(R.id.edit_email);
        passwordView = (EditText)view.findViewById(R.id.edit_pw);
        passwordCheckView = (EditText)view.findViewById(R.id.edit_checkpw);

        Button btn_submit = (Button)view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailView.getText().toString();
                final String password = passwordView.getText().toString();
                final String checkPassword = passwordCheckView.getText().toString();

                if (password.equals(checkPassword) && password.length()>=8) {
                    //Toast.makeText(getContext(), "이메일 전송", Toast.LENGTH_SHORT).show();
                    signUp(email,password);

                }else if (password.equals(checkPassword) && password.length()<8){
                    Toast.makeText(getContext(), "비밀번호를 8자 이상입력하세요 ", Toast.LENGTH_SHORT).show();
                }if (!password.equals(checkPassword)){
                    Toast.makeText(getContext(), "비밀번호가 일치하지 않습니다 ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private void signUp(String id, String pw) {
        NetworkManager.getInstance().signUp(getContext(), id, pw, new NetworkManager.OnResultListener<StatusCheckResult>() {
            @Override
            public void onSuccess(Request request, StatusCheckResult result) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                alert.setMessage("이메일이 전송되었습니다. 인증 후 로그인해주세요.");
                alert.show();
                //Toast.makeText(getContext(),"이메일 인증 후 로그인해주세요 : "+result.status,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(),"회원가입 실패 ",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
