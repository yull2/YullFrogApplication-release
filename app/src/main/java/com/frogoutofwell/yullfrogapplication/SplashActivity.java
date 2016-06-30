package com.frogoutofwell.yullfrogapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.frogoutofwell.yullfrogapplication.data.FacebookUserResult;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.login.AgreementActivity;
import com.frogoutofwell.yullfrogapplication.login.LoginActivity;
import com.frogoutofwell.yullfrogapplication.login.StudentConfirmActivity;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.manager.PropertyManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;

import okhttp3.Request;

public class SplashActivity extends AppCompatActivity {

    Handler mHandler = new Handler(Looper.getMainLooper());
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

     /*   String userEmail = PropertyManager.getInstance().getEmail();
        String userPwd = PropertyManager.getInstance().getPassword();
        String resId = PropertyManager.getInstance().getRegistrationToken();
*/
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                doRealStart();
            }
        };
        setUpIfNeeded();


    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST &&
                resultCode == Activity.RESULT_OK) {
            setUpIfNeeded();
        }
    }

    private void setUpIfNeeded() {
        if (checkPlayServices()) {
            String regId = PropertyManager.getInstance().getRegistrationToken();

            if (!regId.equals("")) {
                doRealStart();
            } else {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void doRealStart() {
        startSplash();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Dialog dialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                dialog.show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void startSplash(){
        String userEmail = PropertyManager.getInstance().getEmail();
        if (!TextUtils.isEmpty(userEmail)) {
            String userPwd = PropertyManager.getInstance().getPassword();
            NetworkManager.getInstance().signIn(this, userEmail, userPwd, PropertyManager.getInstance().getRegistrationToken(), new NetworkManager.OnResultListener<StatusCheckResult>() {
                @Override
                public void onSuccess(Request request, StatusCheckResult result) {
                    if (result.status.equals("OK")) {
                        PropertyManager.getInstance().setLogin(true);
                        goMainActivity();
                    }else if (result.status.equals("notApproval")){
                        Toast.makeText(SplashActivity.this,"약관동의가 필요합니다 ",Toast.LENGTH_SHORT).show();
                        goAgreeActivity();
                    }else if (result.status.equals("notConfirm")){
                        Toast.makeText(SplashActivity.this,"대학생 인증이 필요합니다 ",Toast.LENGTH_SHORT).show();
                        goConfirmActivity();
                    }
                }

                @Override
                public void onFail(Request request, IOException exception) {
                    goLoginActivity();
                }
            });
        }else {
            String facebookId = PropertyManager.getInstance().getFacebookId();
            if (!TextUtils.isEmpty(facebookId)){
                AccessToken token = AccessToken.getCurrentAccessToken();
                if (token == null) {
                    PropertyManager.getInstance().setFacebookId("");
                    goLoginActivity();
                }else {
                    if (facebookId.equals(token.getUserId())){
                        NetworkManager.getInstance().facebookLogin(this, token.getToken(), PropertyManager.getInstance().getRegistrationToken(), new NetworkManager.OnResultListener<FacebookUserResult>() {
                            @Override
                            public void onSuccess(Request request, FacebookUserResult result) {
                                if (result.status.equals("OK")) {
                                    PropertyManager.getInstance().setLogin(true);
                                    PropertyManager.getInstance().setFacebookId(result.facebookId);
                                    goMainActivity();
                                }else if (result.status.equals("notApproval")){
                                    Toast.makeText(SplashActivity.this,"약관동의가 필요합니다 ",Toast.LENGTH_SHORT).show();
                                    PropertyManager.getInstance().setLogin(true);
                                    PropertyManager.getInstance().setFacebookId(result.facebookId);
                                    goAgreeActivity();
                                }else if (result.status.equals("notConfirm")){
                                    Toast.makeText(SplashActivity.this,"대학생 인증이 필요합니다 ",Toast.LENGTH_SHORT).show();
                                    PropertyManager.getInstance().setLogin(true);
                                    PropertyManager.getInstance().setFacebookId(result.facebookId);
                                    goConfirmActivity();
                                } else {
                                    PropertyManager.getInstance().setFacebookId("");
                                    LoginManager.getInstance().logOut();
                                    goLoginActivity();
                                }
                            }

                            @Override
                            public void onFail(Request request, IOException exception) {
                                PropertyManager.getInstance().setFacebookId("");
                                LoginManager.getInstance().logOut();
                                goLoginActivity();
                            }
                        });
                    }else {
                        PropertyManager.getInstance().setFacebookId("");
                        LoginManager.getInstance().logOut();
                        goLoginActivity();
                    }
                }
            }else {
                goLoginActivity();
            }
        }
    }
    private void goMainActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);

    }

    private void goLoginActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }

    private void goAgreeActivity(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, AgreementActivity.class));
                finish();
            }
        }, 2000);
    }

    private void goConfirmActivity(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, StudentConfirmActivity.class));
                finish();
            }
        }, 2000);
    }

 /*   private void getAutoLogin(){
        NetworkManager.getInstance().getAutoUserLogin(this, new NetworkManager.OnResultListener<StatusCheckResult>() {
            @Override
            public void onSuccess(Request request, StatusCheckResult result) {
                if (result.status.equals("OK")) {
                    Toast.makeText(SplashActivity.this, "자동로그인을 실행합니다 : " + result.status, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(SplashActivity.this,"회원가입 실패 : "+exception,Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
