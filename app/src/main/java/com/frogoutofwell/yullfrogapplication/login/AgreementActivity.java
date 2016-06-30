package com.frogoutofwell.yullfrogapplication.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.manager.PropertyManager;

import java.io.IOException;

import okhttp3.Request;

public class AgreementActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "user";
    boolean agree_e, agree_1, agree_2, agree_s, allChecked;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final CheckBox btn_essential = (CheckBox)findViewById(R.id.btn_essential);
        btn_essential.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                agree_e = isChecked;
            }
        });

        final CheckBox btn_agreelook1 = (CheckBox)findViewById(R.id.btn_agreelook1);
        btn_agreelook1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                agree_1 = isChecked;
            }
        });

        final CheckBox btn_agreelook2 = (CheckBox)findViewById(R.id.btn_agreelook2);
        btn_agreelook2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                agree_2 = isChecked;;
            }
        });

        final CheckBox btn_select = (CheckBox)findViewById(R.id.btn_select);
        btn_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                agree_s = isChecked;
            }
        });

        Button btn_allagree = (Button)findViewById(R.id.btn_allagree);
        btn_allagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allChecked == false) {
                    btn_essential.setChecked(true);
                    btn_agreelook1.setChecked(true);
                    btn_agreelook2.setChecked(true);
                    btn_select.setChecked(true);
                    agree_e = true;
                    agree_1 = true;
                    agree_2 = true;
                    agree_s = true;
                    allChecked = true;
                }else {
                    btn_essential.setChecked(false);
                    btn_agreelook1.setChecked(false);
                    btn_agreelook2.setChecked(false);
                    btn_select.setChecked(false);
                    agree_e = false;
                    agree_1 = false;
                    agree_2 = false;
                    agree_s = false;
                    allChecked = false;
                }
            }
        });

        Button btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agree_e && agree_1 && agree_2) {
                    setUserConfirm(1);
                }else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AgreementActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage("약관 동의가 필요합니다.");
                    alert.show();
                   // Toast.makeText(AgreementActivity.this, "약관 동의가 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setUserConfirm(int result){
        NetworkManager.getInstance().getUserAgreement(this, result, new NetworkManager.OnResultListener<StatusCheckResult>() {
            @Override
            public void onSuccess(Request request, StatusCheckResult result) {
                //Toast.makeText(AgreementActivity.this, "약관동의에 성공했습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AgreementActivity.this, StudentConfirmActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }




}
