package com.frogoutofwell.yullfrogapplication.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.frogoutofwell.yullfrogapplication.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    public void changeSignUp() {
        //getSupportActionBar().show();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setTitle(getString(R.string.title_activity_agreement));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SignupFragment())
                .addToBackStack(null)
                .commit();

    }


}
