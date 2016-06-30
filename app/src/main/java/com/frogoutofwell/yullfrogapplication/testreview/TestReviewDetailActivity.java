package com.frogoutofwell.yullfrogapplication.testreview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.TestDetailResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class TestReviewDetailActivity extends AppCompatActivity {

    TextView termView, levelView, resultView, questionView, answerView, wayView;

    int detailSeq;

    String[] level, tresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_review_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        detailSeq = intent.getIntExtra("detailSeq",1);

        level = getResources().getStringArray(R.array.test_level);
        tresult = getResources().getStringArray(R.array.test_result);

        termView = (TextView)findViewById(R.id.text_term);
        levelView = (TextView)findViewById(R.id.text_level);
        resultView = (TextView)findViewById(R.id.text_result);
        questionView = (TextView)findViewById(R.id.text_commendgood);
        answerView = (TextView)findViewById(R.id.text_answer);
        wayView = (TextView)findViewById(R.id.text_way);

        setData();
    }
    private void setData() {
        NetworkManager.getInstance().getInterTestReviewDetail(this, detailSeq, new NetworkManager.OnResultListener<TestDetailResult>() {
            @Override
            public void onSuccess(Request request, TestDetailResult result) {
                getSupportActionBar().setTitle(result.testDetail.getActivityName());
                termView.setText(result.testDetail.getTerm());
                levelView.setText(level[result.testDetail.getLevel()]);
                resultView.setText(tresult[result.testDetail.getResult()]);
                questionView.setText(result.testDetail.getQuestion());
                answerView.setText(result.testDetail.getAnswer());
                wayView.setText(result.testDetail.getWay());
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(TestReviewDetailActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.inter_share){
            AlertDialog.Builder alert = new AlertDialog.Builder(TestReviewDetailActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage("아직 준비중인 서비스 입니다");
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
