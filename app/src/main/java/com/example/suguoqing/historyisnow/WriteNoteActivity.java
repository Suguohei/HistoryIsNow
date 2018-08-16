package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suguoqing.bean.Note;
import com.example.suguoqing.bean.User;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WriteNoteActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "WriteNoteActivity";
    private EditText title;
    private EditText content;
    private Button ok;
    private Button cancel;
    private String name;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让顶部的状态栏和背景合二为一
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_write_note);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        date = intent.getStringExtra("date");

        title = findViewById(R.id.note_title);
        content = findViewById(R.id.note_content);
        ok = findViewById(R.id.note_ok);
        cancel = findViewById(R.id.note_cancle);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.note_cancle:
                finish();
                break;

            case R.id.note_ok:
                String titlename = title.getText().toString();
                String article = content.getText().toString();
                Note note = new Note();

                note.setDate(date);
                note.setUsername(name);
                note.setTime(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));
                if(!("".equals(titlename) && "".equals(article))){
                    note.setTitle(titlename);
                    note.setContent(article);
                    note.save();
                }

                onBackPressed();


                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("name",name);
        intent.putExtra("date",date);
        setResult(RESULT_OK,intent);
        finish();
    }
}
