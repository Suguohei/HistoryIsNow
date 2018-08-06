package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suguoqing.bean.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RegisterActivity";
    private Button login;
    private Button register;
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
        }
    }

    private void register() {
        if(username.getText().toString() == null | "".equals(username.getText().toString())){
            //当输入框为空
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("用户名不能为空")
                    .setPositiveButton("确定",null)
                    .show();
            if(password.getText().toString() == null | "".equals(password.getText().toString())){
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("密码不能为空")
                        .setPositiveButton("确定",null)
                        .show();
            }

        }else{
            String str = username.getText().toString();
            List<User> list = DataSupport.where("name = ?",str).find(User.class);
            if(list != null && list.size() != 0){
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("此用户存在")
                        .setPositiveButton("确定",null)
                        .show();
            }else{
                String name = username.getText().toString();
                String psd = password.getText().toString();
                User user = new User();
                user.setName(name);
                user.setPassword(psd);
                user.save();
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("ok")
                        .setPositiveButton("确定",null)
                        .show();
            }
        }
    }

    private void login() {
        if(username.getText().toString() == null | "".equals(username.getText().toString())){
           //当输入框为空
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("用户名不能为空")
                    .setPositiveButton("确定",null)
                    .show();

       }else{
            String str = username.getText().toString();
            String psd = password.getText().toString();
            List<User> list = DataSupport.where("name = ?",str).find(User.class);
            if(list == null | list.size() == 0){
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("没有此用户")
                        .setPositiveButton("确定",null)
                        .show();
            }else if(!psd.equals(list.get(0).getPassword())){
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("密码不正确")
                        .setPositiveButton("确定",null)
                        .show();
                
            }else{
                //登录进去，把用户保存在缓存中，为了下次打开直接是上次的状态
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username",str);
                editor.apply();
                //登录进去之后跳到主界面,同时清除之前的全部界面
                Intent intent = new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
                Toast.makeText(this, "可以登录进去了", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
