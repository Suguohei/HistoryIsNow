package com.example.suguoqing.util;

import com.example.suguoqing.bean.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class UserCUID {
    //根据name获取一个user
    public  static User getUserByname(String name){
        List<User> list = DataSupport.where("name = ?",name).find(User.class);
        if(list == null | list.size() == 0){
            return  null;
        }
        return list.get(0);
    }


}
