package com.frogoutofwell.yullfrogapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.frogoutofwell.yullfrogapplication.data.InterInfoResult;
import com.frogoutofwell.yullfrogapplication.data.LikeStatusResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;
import com.frogoutofwell.yullfrogapplication.write.WriteDoActivity;
import com.frogoutofwell.yullfrogapplication.write.WriteTestActivity;

import java.io.IOException;

import okhttp3.Request;

public class InterMainActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager pager;

    ImageView logoView;
    TextView nameView, classView;
    MenuItem like_item;

    int seq;
    CheckBox likeCheckBox;
    boolean likeChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        Intent intent = getIntent();
        seq = intent.getIntExtra("seq",1);

        logoView = (ImageView)findViewById(R.id.img_logo);
        nameView = (TextView)findViewById(R.id.text_name);
        classView = (TextView)findViewById(R.id.text_info);


        nameView.setText("대외활동명");
        classView.setText("활동분류");

//        nameView.setTypeface(TypefaceManager.getInstance().getTypeface(getParent(), TypefaceManager.FONT_NAME_NANUM));

        tabs = (TabLayout)findViewById(R.id.tabs);
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new InterMainPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);
        tabs.removeAllTabs();

        tabs.addTab(tabs.newTab().setText("모집요강"));
        tabs.addTab(tabs.newTab().setText("면접후기"));
        tabs.addTab(tabs.newTab().setText("활동후기"));
        tabs.addTab(tabs.newTab().setText("추천"));
        tabs.setBackgroundResource(R.color.colorMainTabs);

        setInterMain();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0090e9")));
        fab.hide();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0 : fab.hide();break;
                    case 1 :
                        fab.show();
                        fab.setImageDrawable(getDrawable(R.drawable.btn_interview3));
                        fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // Toast.makeText(InterMainActivity.this, "1 ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InterMainActivity.this, WriteTestActivity.class);
                            intent.putExtra("seq",seq);
                           // Log.i("intermainactivity"," seqqqqqqqqqqqq : "+seq);
                            startActivity(intent);
                        }
                    }); break;
                    case 2 :
                        fab.show();
                        fab.setImageDrawable(getDrawable(R.drawable.btn_write3));
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               // Toast.makeText(InterMainActivity.this, "2 ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InterMainActivity.this, WriteDoActivity.class);
                                intent.putExtra("seq",seq);
                                startActivity(intent);
                            }
                        }); break;
                    case 3 : fab.hide();break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inter, menu);
        like_item = menu.findItem(R.id.inter_like);

        likeCheckBox = (CheckBox)like_item.getActionView().findViewById(R.id.like_item);
        likeCheckBox.setChecked(likeChecked);
        likeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeStatusChange(seq);
                //Toast.makeText(InterMainActivity.this,"찜활동에 추가되었습니다",Toast.LENGTH_SHORT).show();
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.inter_like){
            return false;
        }
        if (id == R.id.inter_share){
            AlertDialog.Builder alert = new AlertDialog.Builder(InterMainActivity.this);
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

    private void setInterMain(){
        NetworkManager.getInstance().getFrogInterInfo(this, seq, new NetworkManager.OnResultListener<InterInfoResult>() {
            @Override
            public void onSuccess(Request request, InterInfoResult result) {
                Glide.with(InterMainActivity.this).load(result.detail.activityDetail.getCompanyLogo()).into(logoView);
                nameView.setText(result.detail.activityDetail.getName());
                classView.setText(result.detail.activityDetail.getActClass()+" / "+result.detail.activityDetail.getCompanyName());
                if (result.detail.check == 1){
                    likeChecked = true;
                    likeCheckBox.setChecked(likeChecked);

                }
            }

            @Override
            public void onFail(Request request, IOException exception) {

            }
        });
    }

    private void likeStatusChange(int activitySeq){
        NetworkManager.getInstance().getLikeStatusChange(this, activitySeq, new NetworkManager.OnResultListener<LikeStatusResult>() {
            @Override
            public void onSuccess(Request request, LikeStatusResult result) {
                // 아이콘 변경 코드 추가 해야 함
                if (result.status.equals("OK")){
                    if (likeChecked){
                        likeChecked = false;
                    }else likeChecked = true;
                    likeCheckBox.setChecked(likeChecked);
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(InterMainActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
