package com.frogoutofwell.yullfrogapplication.account;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class PWChangeActivity extends AppCompatActivity {

    EditText currentView, newpwView, newpwcheckView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwchange);
        setTitle("비밀번호 변경");

        currentView = (EditText)findViewById(R.id.edit_current);
        newpwView = (EditText)findViewById(R.id.edit_newpw);
        newpwcheckView = (EditText)findViewById(R.id.edit_newpwcheck);

        Button cancel = (Button)findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button save = (Button)findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = currentView.getText().toString();
                String pw = newpwView.getText().toString();
                String pwcheck = newpwcheckView.getText().toString();
                if (pw.equals(pwcheck) && pw.length()>= 8){
                    //Toast.makeText(PWChangeActivity.this,"비밀번호 일치",Toast.LENGTH_SHORT).show();
                    setNewPassword(current,pw);
                }else if (pw.equals(pwcheck) && pw.length() < 8){
                    Toast.makeText(PWChangeActivity.this,"8자 이상 입력해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PWChangeActivity.this,"다시 입력해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setNewPassword(String current, String newpwd ){
        NetworkManager.getInstance().getUserPWChange(this, current, newpwd, new NetworkManager.OnResultListener<StatusCheckResult>() {
            @Override
            public void onSuccess(Request request, StatusCheckResult result) {
                if (result.status.equals("OK")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(PWChangeActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alert.setMessage("비밀번호가 변경되었습니다.");
                    alert.show();
                    //Toast.makeText(PWChangeActivity.this,"비밀번호가 변경되었습니다."+result,Toast.LENGTH_SHORT).show();
                }else if(result.status.equals("notEqual")) {
                    Toast.makeText(PWChangeActivity.this,"현재 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PWChangeActivity.this,"비밀번호 변경에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(PWChangeActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }





}
