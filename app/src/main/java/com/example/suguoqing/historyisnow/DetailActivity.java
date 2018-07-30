package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suguoqing.util.RetrofitTool;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private static final String TAG = "DetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        recyclerView = findViewById(R.id.recycle_view);

        //1.获取年月日
        Intent intent = getIntent();
        int year = intent.getIntExtra("year",0);
        int month = intent.getIntExtra("month",0);
        int day = intent.getIntExtra("day",0);

        //2.根据年月日获取数据
        requestData(year,month,day);
        //3.设置一下ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void requestData(int year,int month,int day) {
        String address = "http://api.juheapi.com/japi/";
        RetrofitTool.sendRetrofitRequest(month,day,address, new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                List<Detail> list = response.body().getResult();
                DetailAdapter adapter = new DetailAdapter(list);
                GridLayoutManager layoutManager = new GridLayoutManager(DetailActivity.this,2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
