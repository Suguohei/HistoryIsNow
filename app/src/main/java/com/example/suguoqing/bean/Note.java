package com.example.suguoqing.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;

public class Note extends DataSupport implements Serializable{
    private String title;
    private String content;
    private String date;
    private String note_img;
    private String username;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote_img() {
        return note_img;
    }

    public void setNote_img(String note_img) {
        this.note_img = note_img;
    }


    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", note_img='" + note_img + '\'' +
                ", username='" + username + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
