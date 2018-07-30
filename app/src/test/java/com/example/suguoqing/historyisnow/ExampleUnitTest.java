package com.example.suguoqing.historyisnow;

import android.widget.Toast;

import com.example.suguoqing.util.RetrofitTool;

import org.junit.Test;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testRetrofitTool(){
        String address = "http://api.juheapi.com/japi/";
        final Object lock = new Object();

        RetrofitTool.sendRetrofitRequest(12,12,address, new Callback<History>() {

            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                if(response != null){
                    System.out.println("sdafasdfasdfasdfasfd");
                    System.out.println(response.body().toString());

                }
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                System.out.println("failed");
            }
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void testHandlerResponse(){

    }

    @Test
    public void testsendOkhttpRequest(){
        String url = "http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200905/17/6C231817233.jpg";
        final Object lock = new Object();
        RetrofitTool.sendOkhttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
               System.out.println("failed");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if(response != null){
                    System.out.println("chenggong");
                    System.out.println(response.body().toString());
                }
                synchronized (lock){
                    lock.notify();
                }
            }
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}