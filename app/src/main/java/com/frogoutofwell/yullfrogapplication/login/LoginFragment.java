package com.frogoutofwell.yullfrogapplication.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.frogoutofwell.yullfrogapplication.MainActivity;
import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.FacebookUserResult;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.manager.PropertyManager;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    String userEmail, userPwd;

    Button facebookLoginButton;
    EditText emailView,passwordView;

    CallbackManager callbackManager;
    LoginManager loginManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailView = (EditText)view.findViewById(R.id.edit_email);
        passwordView = (EditText)view.findViewById(R.id.edit_password);

        Button btn_login = (Button)view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailView.getText().toString();
                final String password = passwordView.getText().toString();
                String resId = PropertyManager.getInstance().getRegistrationToken();
              //  Log.i("loggggggggggggggggi","loggggggggggggggggi : "+resId);
                login(email, password, resId);
                userEmail = email;
                userPwd = password;

            }
        });


        facebookLoginButton = (Button)view.findViewById(R.id.btn_login_facebook);
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login();
                login();
            }
        });
        Button btn_signup = (Button)view.findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();

        return view;
    }

    private void login() {
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken token = AccessToken.getCurrentAccessToken();
                String resId = PropertyManager.getInstance().getRegistrationToken();
                NetworkManager.getInstance().facebookLogin(getContext(), token.getToken(), resId, new NetworkManager.OnResultListener<FacebookUserResult>() {
                    @Override
                    public void onSuccess(Request request, FacebookUserResult result) {
                        if (result.status.equals("OK")){
                            // Toast.makeText(getContext(),"로그인 : "+result.status,Toast.LENGTH_SHORT).show();
                            PropertyManager.getInstance().setLogin(true);
                            PropertyManager.getInstance().setFacebookId(result.facebookId);
                            goMainActivity();
                        }else if (result.status.equals("notApproval")){
                            Toast.makeText(getContext(),"약관동의가 필요합니다 ",Toast.LENGTH_SHORT).show();
                            PropertyManager.getInstance().setLogin(true);
                            PropertyManager.getInstance().setFacebookId(result.facebookId);
                           goAgreeActivity();
                        }else if (result.status.equals("notConfirm")){
                            Toast.makeText(getContext(),"대학생 인증이 필요합니다 ",Toast.LENGTH_SHORT).show();
                            PropertyManager.getInstance().setLogin(true);
                            PropertyManager.getInstance().setFacebookId(result.facebookId);
                            goConfirmActivity();
                        }else {
                            Toast.makeText(getContext(),"회원 가입이 필요합니다 ",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                        Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        loginManager.logInWithReadPermissions(this, Arrays.asList("email"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void login(final String id, String pw, String resId) {
        NetworkManager.getInstance().signIn(getContext(), id, pw, resId, new NetworkManager.OnResultListener<StatusCheckResult>() {
            @Override
            public void onSuccess(Request request, StatusCheckResult result) {
                //Toast.makeText(getContext(),"로그인 : "+result.status,Toast.LENGTH_SHORT).show();
                if (result.status.equals("OK")){
                   // Toast.makeText(getContext(),"로그인 : "+result.status,Toast.LENGTH_SHORT).show();
                    PropertyManager.getInstance().setEmail(userEmail);
                    PropertyManager.getInstance().setPassword(userPwd);
                    PropertyManager.getInstance().setLogin(true);
                    goMainActivity();
                }else if (result.status.equals("notApproval")){
                    Toast.makeText(getContext(),"약관동의가 필요합니다 ",Toast.LENGTH_SHORT).show();
                    goAgreeActivity();
                }else if (result.status.equals("notConfirm")){
                    Toast.makeText(getContext(),"대학생 인증이 필요합니다 ",Toast.LENGTH_SHORT).show();
                    goConfirmActivity();
                }else {
                    Toast.makeText(getContext(),"회원 가입이 필요합니다 ",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(getContext(), "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void signup() {
        ((LoginActivity)getActivity()).changeSignUp();
    }

    private void goMainActivity(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void goAgreeActivity(){
        Intent intent = new Intent(getContext(), AgreementActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void goConfirmActivity(){
        Intent intent = new Intent(getContext(), StudentConfirmActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
