package com.frogoutofwell.yullfrogapplication.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.AccountInfoResult;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.login.LoginActivity;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.manager.PropertyManager;

import java.io.IOException;

import okhttp3.Request;

public class AccountActivity extends AppCompatActivity {

    Button btn_email;
    CheckBox btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        btn_email = (Button)findViewById(R.id.btn_email);
        btn_confirm = (CheckBox)findViewById(R.id.btn_confirm);
        setData();

        Button btn_pwchange = (Button)findViewById(R.id.btn_pwchange);
        btn_pwchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, PWChangeActivity.class);
                startActivity(intent);
            }
        });

        Button btn_actclass = (Button)findViewById(R.id.btn_actclass);
        btn_actclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, ActclassChangeActivity.class);
                startActivity(intent);
            }
        });

        Button btn_indus = (Button)findViewById(R.id.btn_indus);
        btn_indus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, IndusChangeActivity.class);
                startActivity(intent);
            }
        });

        Button btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AccountActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getUserLogoutRequest();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setMessage("로그아웃 하시겠습니까?");
                alert.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData(){
        NetworkManager.getInstance().getAccountUserInfo(this, new NetworkManager.OnResultListener<AccountInfoResult>() {
            @Override
            public void onSuccess(Request request, AccountInfoResult result) {
                String email = result.email;
                btn_email.setText(email);
                if (result.confirmCheck==1){
                    btn_confirm.setChecked(true);
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }



    private void getUserLogoutRequest(){
        NetworkManager.getInstance().getUserLogout(this, new NetworkManager.OnResultListener<StatusCheckResult>() {
            @Override
            public void onSuccess(Request request, StatusCheckResult result) {
                if (result.status.equals("OK")){
                    PropertyManager.getInstance().setEmail(null);
                    PropertyManager.getInstance().setPassword(null);
                    PropertyManager.getInstance().setFacebookId(null);
                    PropertyManager.getInstance().setLogin(false);
                    PropertyManager.getInstance().setUser(null);

                    Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(AccountActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
