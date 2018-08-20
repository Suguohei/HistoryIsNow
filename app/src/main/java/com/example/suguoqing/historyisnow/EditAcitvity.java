package com.example.suguoqing.historyisnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suguoqing.bean.Note;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditAcitvity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "EditAcitvity";
    private EditText edit_title;
    private EditText edit_content;
    private Button edit_ok;
    private Button edit_cancel;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_acitvity);
        edit_title = findViewById(R.id.edit_title);
        edit_content = findViewById(R.id.edit_content);
        edit_ok = findViewById(R.id.edit_ok);
        edit_cancel = findViewById(R.id.edit_cancel);
        edit_ok.setOnClickListener(this);
        edit_cancel.setOnClickListener(this);

        Intent intent = getIntent();
        currentNote = (Note) intent.getSerializableExtra("currentNote");
        String title = currentNote.getTitle();
        String content = currentNote.getContent();
        edit_title.setText(title);
        edit_content.setText(content);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_cancel:
                finish();
                break;

            case R.id.edit_ok:
                String titlename = edit_title.getText().toString();
                String article = edit_content.getText().toString();
                List<Note> notes = DataSupport
                        .where("username = ? and date = ? and time = ?"
                                ,currentNote.getUsername(),currentNote.getDate(),currentNote.getTime()).find(Note.class);
                //currentNote.setTime(new SimpleDateFormat("yyyy/M/dd hh:mm:ss").format(new Date()));
                Note note = notes.get(0);
                note.setTime(new SimpleDateFormat("yyyy/M/dd hh:mm:ss").format(new Date()));
                if(!("".equals(titlename) && "".equals(article))){
                    note.setTitle(titlename);
                    note.setContent(article);
                    note.save();
                }

               Intent intent = new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }


}
