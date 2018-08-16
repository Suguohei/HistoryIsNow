package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.UserManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.suguoqing.bean.Note;
import com.example.suguoqing.bean.User;
import com.example.suguoqing.util.UserCUID;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

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
        private ImageView nav_backimg;
        private String selectedDate;
        private RecyclerView note_recycle;

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
        note_recycle = findViewById(R.id.note_recycle);
        View drawerView = navigationView.inflateHeaderView(R.layout.nav_header);//把头部加进去

        //实现头部监听
        circleImageView = drawerView.findViewById(R.id.icon_image);
        nav_backimg = drawerView.findViewById(R.id.nav_backimg);
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
                selectedDate = year+"/"+(month+1)+"/"+day;
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
                        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent);
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
//每次打开导航栏都刷新
    private void reflashDrawer() {
        //判断shareprefence中有没有缓存，如果有说明已经登录过了，就可以直接在头像下面的状态栏显示姓名
        SharedPreferences preferences = getSharedPreferences("com.example.suguoqing.historyisnow_preferences",MODE_PRIVATE);
        String name = preferences.getString("username","");
        if(name != null && name != ""){
            username.setText(name);
            User user = UserCUID.getUserByname(name);
            if(user != null){
                //判断有没有设置头像和邮箱等信息
                if(user.getEmail() != null && user.getEmail() != ""){
                    email.setText(user.getEmail());
                }
                if(user.getImg() != null){
                    //处理头像
                    refreshHeadPhoto(name);
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_btn:
                Toast.makeText(this, "hahah  dian ji wo ", Toast.LENGTH_SHORT).show();
                headlerBtn();
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

    /**
     * 点击floatbutton
     */
    private void headlerBtn() {
        reflashDrawer();
        //判断有没有登录
        if("".equals(username.getText()) || username.getText() == null){
            Toast.makeText(this, "没有登录", Toast.LENGTH_SHORT).show();
        }else {
            //获取这个user，根据username来获取
            String name = (String) username.getText();
            Intent intent = new Intent(this,WriteNoteActivity.class);
            intent.putExtra("name",name);
            if(selectedDate == null){
                SimpleDateFormat spformat = new SimpleDateFormat("yyyy/MM/dd");
                selectedDate = spformat.format(new Date()).toString();
            }
            intent.putExtra("date",selectedDate);
            startActivityForResult(intent,2);
        }
    }

    //判断用户有没有登录处理头像，如果登录就到设置头像界面，如果没有就到登录注册界面。
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
            Intent intent = new Intent(this,HeadInfoActivity.class);
            intent.putExtra("name",name);
            startActivityForResult(intent,1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    Log.d(TAG, "onActivityResult: "+name);
                    refreshHeadPhoto(name);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    String name = data.getStringExtra("name");
                    String date = data.getStringExtra("date");
                    refreshNotes(name,date);
                }

                break;
        }
    }

    /**
     *在日历下显示note
     */
    private void refreshNotes(String name,String date) {
        List<Note> notes = DataSupport.where("username = ? and date = ?", name,date).find(Note.class);
        Log.d(TAG, "refreshNotes: aaa"+notes.size());
        for(Note n : notes){
            Log.d(TAG, "refreshNotes: aaa"+n);
        }
        if(notes != null && notes.size() != 0){
           NoteAdapter adapter = new NoteAdapter(notes,this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            note_recycle.setLayoutManager(manager);
            note_recycle.setAdapter(adapter);
            note_recycle.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        }

    }


    /**
     * 处理重新打开侧栏刷新图像
     */
    private void refreshHeadPhoto(String name){
        User user = DataSupport.where("name = ?",name)
                .find(User.class).get(0);
        if(user.getImg() != null){
            File file = new File(user.getImg());
            if(file.exists()){
                Uri outputUri = Uri.fromFile(file);

                Glide.with(this)
                        .load(outputUri)
                        .into(circleImageView);


                Glide.with(this)
                        .load(outputUri)
                        .bitmapTransform(new BlurTransformation(this, 20))
                        .into(nav_backimg);

            }

        }
    }
}
