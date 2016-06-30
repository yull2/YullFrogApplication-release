package com.frogoutofwell.yullfrogapplication.doreview;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.DoDetailResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class DoReviewDetailActivity extends AppCompatActivity {

    TextView commentView, rateView, termView, writeDateView, commentGoodView, commentBadView;
    ImageView rateBar;

    int seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_review_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        seq = intent.getIntExtra("detailSeq",1);

        commentView = (TextView)findViewById(R.id.text_comment);
        rateView = (TextView)findViewById(R.id.text_rate);
        termView = (TextView)findViewById(R.id.text_term);
        writeDateView = (TextView)findViewById(R.id.text_writedate);
        commentGoodView = (TextView)findViewById(R.id.text_commendgood);
        commentBadView = (TextView)findViewById(R.id.text_commentbad);
        rateBar = (ImageView)findViewById(R.id.img_rate);

        setData();
    }

    private void setData() {
        NetworkManager.getInstance().getInterDoReviewDetail(this, seq, new NetworkManager.OnResultListener<DoDetailResult>() {
            @Override
            public void onSuccess(Request request, DoDetailResult result) {
                //Log.i("search","search===================="+result.doDetail.getActivityName());
                getSupportActionBar().setTitle(result.doDetail.getActivityName());
                commentView.setText(result.doDetail.getComment());
                rateView.setText(result.doDetail.getRate() +" ");
                termView.setText(result.doDetail.getTerm());
                writeDateView.setText(result.doDetail.getWriteDate());
                commentGoodView.setText(result.doDetail.getCommentGood());
                commentBadView.setText(result.doDetail.getCommentBad());

                if (result.doDetail.getRate() == 0){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star0);
                }else if (result.doDetail.getRate() == 0.5){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star0_1);
                }else if (result.doDetail.getRate() == 1){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star1);
                }else if (result.doDetail.getRate() == 1.5){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star1_1);
                }else if (result.doDetail.getRate() == 2){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star2);
                }else if (result.doDetail.getRate() == 2.5){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star2_1);
                }else if (result.doDetail.getRate() == 3){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star3);
                }else if (result.doDetail.getRate() == 3.5){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star3_1);
                }else if (result.doDetail.getRate() == 4){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star4);
                }else if (result.doDetail.getRate() == 4.5){
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star4_1);
                }else {
                    rateBar.setImageResource(R.drawable.activityreview_detail_ic_star5);
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(DoReviewDetailActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder alert = new AlertDialog.Builder(DoReviewDetailActivity.this);
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
