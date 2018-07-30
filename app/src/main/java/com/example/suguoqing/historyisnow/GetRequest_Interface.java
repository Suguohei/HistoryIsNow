package com.example.suguoqing.historyisnow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRequest_Interface {


    @GET("toh?key=c61b726aa4e2919b720a212ecfdc5b58&v=1.0")
    Call<History> getCall(@Query("month") int month,@Query("day") int day);
}
