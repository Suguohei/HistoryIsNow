package com.example.suguoqing.historyisnow;

import java.io.Serializable;

public class Detail implements Serializable {

    private String day;
    private String des;
    private String id;
    private String lunar;
    private String month;
    private String pic;
    private String title;
    private String year;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "day='" + day + '\'' +
                ", des='" + des + '\'' +
                ", id='" + id + '\'' +
                ", lunar='" + lunar + '\'' +
                ", month='" + month + '\'' +
                ", pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
