package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class AllInfoActivity extends AppCompatActivity {
    private static final String TAG = "AllInfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_info);
        //1.获取Detail
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        Detail detail = (Detail) bundle.getSerializable("detail");
        //System.out.println(detail);

        //2.在Toolbar上显示标题
        Toolbar toolbar = findViewById(R.id.all_info_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(detail.getLunar());
       }

        //3.填充内容
        TextView title = findViewById(R.id.all_info_title);
        ImageView imageView = findViewById(R.id.all_info_img);
        TextView textView = findViewById(R.id.all_info_text);

        title.setText(detail.getTitle());
        Glide.with(this).load(detail.getPic()).into(imageView);
        textView.setText(detail.getDes());
    }
}
