package com.example.suguoqing.util;


import com.example.suguoqing.historyisnow.GetRequest_Interface;
import com.example.suguoqing.historyisnow.History;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTool {

    //请求数据
    public static void sendRetrofitRequest(int year,int day,String address, Callback<History> callback ){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(address)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<History> call = request.getCall(year,day);

        call.enqueue(callback);
    }

    //请求pic用okhttp
    public static void sendOkhttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }

}
