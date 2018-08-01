package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.os.Binder;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.suguoqing.bean.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

        private Toolbar toolbar;
        private NavigationView nav_view;
        private MaterialCalendarView materialCalendarView;
        private DrawerLayout drawerLayout;
        private FloatingActionButton btn;
        private NavigationView navigationView;
        private CircleImageView circleImageView;
        private TextView username;
        private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------------------
        toolbar = findViewById(R.id.toolbar);
        nav_view = findViewById(R.id.nav_view);
        materialCalendarView = findViewById(R.id.calendar_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        btn = findViewById(R.id.float_btn);
        navigationView = findViewById(R.id.nav_view);
        View drawerView = navigationView.inflateHeaderView(R.layout.nav_header);//把头部加进去
        //实现头部监听
        circleImageView = drawerView.findViewById(R.id.icon_image);
        username = drawerView.findViewById(R.id.username);
        email = drawerView.findViewById(R.id.email);
        circleImageView.setOnClickListener(this);
        username.setOnClickListener(this);
        email.setOnClickListener(this);


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
        //默认选中
        Date date = new Date();
        materialCalendarView.setSelectedDate(date);


        //监听nav_view中的内容
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_shin:
                        Toast.makeText(MainActivity.this, "shin", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(MainActivity.this, "shin", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_about:
                        Toast.makeText(MainActivity.this, "shin", Toast.LENGTH_SHORT).show();
                        break;
                        default:
                            break;
                }
                return true;
            }
        });



    }


    //监听Toolbar上的按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                //打开之后刷新
                reflashDrawer();
        }
        return true;
    }
//处理刷新
    private void reflashDrawer() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_btn:
                Toast.makeText(this, "hahah  dian ji wo ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.icon_image:
                Toast.makeText(this, "icom_image", Toast.LENGTH_SHORT).show();
                //处理头像
                handlerHead();
                break;
            case R.id.username:
                Toast.makeText(this, "username", Toast.LENGTH_SHORT).show();
                break;

            case R.id.email:
                Toast.makeText(this, "email ", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    //处理头像
    private void handlerHead() {
        //判断有没有登录
        if("".equals(username.getText()) || username.getText() == null){
            //创建数据库
            Connector.getDatabase();
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }else {
            //获取这个user，根据username来获取
            String name = (String) username.getText();
            List<User> users = DataSupport.where("name = ?",name).find(User.class);
            Intent intent = new Intent(this,HeadInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",users.get(0));
            intent.putExtra("bundler",bundle);
            startActivity(intent);
        }

    }


}
