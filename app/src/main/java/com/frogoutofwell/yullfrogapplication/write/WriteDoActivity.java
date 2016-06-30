package com.frogoutofwell.yullfrogapplication.write;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetailResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class WriteDoActivity extends AppCompatActivity {

    TextView infoView;
    EditText commentView, commentGoodView, commentBadView;
    RatingBar ratebar;
    Spinner spinner_term;
    String tmp_term;
    int seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        seq = intent.getIntExtra("seq",1);

        setContentView(R.layout.activity_write_do);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        //Log.i("seeeeeeq","전달되누값 "+ seq);

        infoView = (TextView)findViewById(R.id.text_info);
        commentView = (EditText)findViewById(R.id.edit_comment);
        commentGoodView = (EditText)findViewById(R.id.edit_commentgood);
        commentBadView = (EditText)findViewById(R.id.edit_commentbad);
        ratebar = (RatingBar)findViewById(R.id.ratebar);
        spinner_term = (Spinner)findViewById(R.id.spinner_term);

        setData();

        spinner_term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(WriteDoActivity.this, "term // position : "+ position+", id : "+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                tmp_term = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               // Log.i("rating","rating :  "+rating+", ratebar.getRating() :"+ratebar.getRating());
            }
        });

        Button btn_upload = (Button)findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentView.getText().toString();
                String commentGood = commentGoodView.getText().toString();
                String commentBad = commentBadView.getText().toString();
                float rate = ratebar.getRating();
                String term = tmp_term;
                reviewUpload(seq, rate, term, comment, commentGood, commentBad);
            }
        });
    }

    private void reviewUpload( int activitySeq, float rate, String term, String comment, String commentGood, String commentBad){
       // Log.i("reviewUpload", "reviewUpload : "+rate+", comment: "+comment+", commentGood : "+commentGood+", commentBad: "+commentBad);
        NetworkManager.getInstance().getFrogDoReviewPost(this, activitySeq, rate, term, comment, commentGood, commentBad, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(Request request, String result) {
                //Log.i("seeeeeeq","전달되누값 "+ seq);
                String status = result;
                //Toast.makeText(WriteDoActivity.this, "업로드 상태 "+ status, Toast.LENGTH_LONG).show();
                if (status.equals("OK")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(WriteDoActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alert.setMessage("활동후기가 업로드 되었습니다. 10개굴이 적립됩니다.");
                    alert.show();

                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(WriteDoActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData() {
        NetworkManager.getInstance().getInterClassInfo(this, seq, new NetworkManager.OnResultListener<ActivityDetailResult>() {
            @Override
            public void onSuccess(Request request, ActivityDetailResult result) {
               // Log.i("writedo","result"+result.activityDetail.companyName);
                String actName = result.activityDetail.getName();
                String actClass = result.activityDetail.getActClass();
                String comName = result.activityDetail.getCompanyName();
                String region = result.activityDetail.getRegion();
                infoView.setText(actName+" / "+actClass+" / "+comName+" / "+region);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(WriteDoActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
