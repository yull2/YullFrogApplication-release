package com.frogoutofwell.yullfrogapplication.account;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.frogoutofwell.yullfrogapplication.R;
import com.frogoutofwell.yullfrogapplication.data.StatusCheckResult;
import com.frogoutofwell.yullfrogapplication.manager.NetworkManager;

import java.io.IOException;

import okhttp3.Request;

public class IndusChangeActivity extends AppCompatActivity {

    String[] indus;
    MenuItem saveItem;

    RecyclerView listView;
    IndusChangeAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indus_change);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Resources res = getResources();
        indus = res.getStringArray(R.array.indus);

        mAdapter = new IndusChangeAdapter();
        listView = (RecyclerView)findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);

        mAdapter.setOnItemClickListener(new IndusViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String str, int postion) {
                //Log.i("actclass", "actclass : "+str+", postion"+postion);
                mAdapter.onItemClick(view, str,postion);
            }
        });

        setData();
    }

    private void setData() {
        mAdapter.clear();
        mAdapter.addAll(indus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attract, menu);
        saveItem = menu.findItem(R.id.save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.save){
            SparseBooleanArray result = mAdapter.getCheckedItems();
           /* for (int i=0;i<11;i++){
               // Log.i("induschanact","과연??? 저장된 값 " +i+" = "+ result.get(i));
            }*/
            setIndusChange(result);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setIndusChange(SparseBooleanArray result){
        NetworkManager.getInstance().getMyIndusChange(this, result, new NetworkManager.OnResultListener<StatusCheckResult>() {
            @Override
            public void onSuccess(Request request, StatusCheckResult result) {
                if (result.status.equals("OK")){
                    //Toast.makeText(IndusChangeActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(IndusChangeActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    alert.setMessage("관심 산업군 설정이 완료되었습니다.");
                    alert.show();
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
                Toast.makeText(IndusChangeActivity.this, "fail : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
