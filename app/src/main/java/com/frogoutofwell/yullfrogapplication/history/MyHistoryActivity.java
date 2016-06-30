package com.frogoutofwell.yullfrogapplication.history;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.frogoutofwell.yullfrogapplication.R;

public class MyHistoryActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        tabs = (TabLayout)findViewById(R.id.tabs);
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new MyHistoryPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);
        tabs.removeAllTabs();

        tabs.addTab(tabs.newTab().setText("면접 후기"));
        tabs.addTab(tabs.newTab().setText("활동 후기"));

        tabs.setBackgroundResource(R.color.colorMainTabs);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
