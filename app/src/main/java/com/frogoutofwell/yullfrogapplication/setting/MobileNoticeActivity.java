package com.frogoutofwell.yullfrogapplication.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.data.UserAlarmResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class MobileNoticeActivity extends AppCompatActivity {

    boolean mobile, notice, likeAct;
    SwitchCompat push_mobile, push_notice, push_likeact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_notice);
        setTitle(getString(R.string.notice_mobile));

        push_mobile = (SwitchCompat)findViewById(R.id.p_mobile);
        push_notice = (SwitchCompat)findViewById(R.id.p_likenotice);
        push_likeact = (SwitchCompat)findViewById(R.id.p_likeact);

         setData();

        push_mobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mobile = isChecked;
                if (mobile == false){
                    push_notice.setChecked(false);
                    push_likeact.setChecked(false);
                    notice = false;
                    likeAct = false;
                   // Log.i("mobile","onchecked :"+notice+likeAct);
                }
            }
        });


        push_mobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                notice = isChecked;
            }
        });


        push_likeact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                likeAct = isChecked;
            }
        });


        Button btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tmp = mobile?1:0;
                int tmp1 = notice?1:0;
                int tmp2 = likeAct?1:0;
               // Log.i("mobile","ttttttttttttttmp :"+tmp+tmp1+tmp2);

                NetworkManager.getInstance().getUserAlarm(this, tmp, tmp1, tmp2, new NetworkManager.OnResultListener<StatusCheckResult>() {
                    @Override
                    public void onSuccess(Request request, StatusCheckResult result) {
                        if (result.status.equals("OK")){
                            finish();
                        }
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {

                    }
                });
            }
        });
    }

    private void setData(){
        NetworkManager.getInstance().getUserAlarm(this, new NetworkManager.OnResultListener<UserAlarmResult>() {
            @Override
            public void onSuccess(Request request, UserAlarmResult result) {
                int tmp = result.mobile;
                int tmp1 = result.notice;
                int tmp2 = result.likeAct;
                if (tmp == 1){
                    mobile = true;
                    push_mobile.setChecked(true);
                }
                if (tmp1 == 1){
                    notice = true;
                    push_notice.setChecked(true);
                }
                if (tmp2 == 1){
                    likeAct = true;
                    push_likeact.setChecked(true);
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }
}
