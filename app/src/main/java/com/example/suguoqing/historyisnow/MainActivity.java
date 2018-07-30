package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

        private Toolbar toolbar;
        private NavigationView nav_view;
        private MaterialCalendarView materialCalendarView;
        private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------------------
        toolbar = findViewById(R.id.toolbar);
        nav_view = findViewById(R.id.nav_view);
        materialCalendarView = findViewById(R.id.calendar_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        FloatingActionButton btn = findViewById(R.id.float_btn);
        btn.setOnClickListener(this);

        //设置ToolBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            //actionBar.setIcon(R.mipmap.img_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //监听日历
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth();
                int day = date.getDay();
                Toast.makeText(MainActivity.this, ""+year+"年"+(month+1)+"月"+day+"日", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month+1);
                intent.putExtra("day",day);
                startActivity(intent);

            }
        });



    }


    //监听Toolbar上的按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_btn:
                Toast.makeText(this, "hahah  dian ji wo ", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
