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
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.ActivityDetailResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class WriteTestActivity extends AppCompatActivity {

    TextView infoView;
    EditText questionView, answerView, wayView;
    Spinner spinner_term, spinner_level;
    RadioGroup radio_result;
    String tmp_term;
    int tmp_level,seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        seq = intent.getIntExtra("seq",1);

        infoView = (TextView)findViewById(R.id.text_info);
        questionView = (EditText)findViewById(R.id.edit_question);
        answerView = (EditText)findViewById(R.id.edit_answer);
        wayView = (EditText)findViewById(R.id.edit_way);
        spinner_term = (Spinner)findViewById(R.id.spinner_term);
        spinner_level = (Spinner)findViewById(R.id.spinner_level);
        radio_result = (RadioGroup)findViewById(R.id.radio_result);

        setData();

        spinner_term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(WriteTestActivity.this, "term // position : "+ position+", id : "+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                tmp_term = parent.getItemAtPosition(position).toString();
                //Log.i("tmp_term","tmp_term : "+tmp_term);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(WriteTestActivity.this, "level// position : "+ position+", id : "+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
                tmp_level = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radio_result.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                /*if (checkedId == R.id.radio_s) {
                    Toast.makeText(WriteTestActivity.this,"합격",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(WriteTestActivity.this,"불합격",Toast.LENGTH_SHORT).show();*/
            }
        });

        Button btn_upload = (Button)findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionView.getText().toString();
                String answer = answerView.getText().toString();
                String way = wayView.getText().toString();
                String term = tmp_term;
                int level = tmp_level;
                int test_result = 1;
               // Log.i("term","term : "+term);
                //Log.i("level","level : "+level);
                int id = radio_result.getCheckedRadioButtonId();
                if (id == R.id.radio_s) test_result = 0;
                else test_result = 1;

                reviewUpload(seq, level, test_result, term, question, answer, way);
            }
        });

    }

    private void reviewUpload(int activitySeq, int level, int test_result,
                              String term, String question, String answer, String way){
        NetworkManager.getInstance().getFrogTestReviewPost(this, activitySeq, level, test_result, term, question, answer, way, new NetworkManager.OnResultListener<String>() {
            @Override
            public void onSuccess(Request request, String result) {
                String status = result;
               //Toast.makeText(WriteTestActivity.this, "업로드 상태 "+ status, Toast.LENGTH_LONG).show();
                if (status.equals("OK")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(WriteTestActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alert.setMessage("면접후기가 업로드 되었습니다. 10개굴이 적립됩니다.");
                    alert.show();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(WriteTestActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setData() {
        NetworkManager.getInstance().getInterClassInfo(this, seq, new NetworkManager.OnResultListener<ActivityDetailResult>() {
            @Override
            public void onSuccess(Request request, ActivityDetailResult result) {
                String actName = result.activityDetail.getName();
                String actClass = result.activityDetail.getActClass();
                String comName = result.activityDetail.getCompanyName();
                String region = result.activityDetail.getRegion();
                infoView.setText(actName+" / "+actClass+" / "+comName+" / "+region);
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(WriteTestActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
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
