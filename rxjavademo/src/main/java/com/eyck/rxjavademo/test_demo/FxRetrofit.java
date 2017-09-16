package com.eyck.rxjavademo.test_demo;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eyck on 2017/8/31.
 */

public class FxRetrofit {
    private String TAG = FxRetrofit.class.getSimpleName();

    public void call(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fx.scnu.edu.cn/api/getpostlist.ashx/")
                .build();
        BlogService service = retrofit.create(BlogService.class);
        Call<ResponseBody> call = service.testFormUrlEncoded1(1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.w(TAG,response.body().string());
                    Log.w(TAG,call.request().url().toString());
                    Log.w(TAG,call.request().headers().toString());
                    Log.w(TAG,call.request().method().toString());
//                    Log.w(TAG,call.request().body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void post(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fx.scnu.edu.cn/api/getpostlist.ashx/")
                .build();
        BlogService service = retrofit.create(BlogService.class);
        Call<ResponseBody> call = service.createUser(new User("eyck",21));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.w(TAG,response.body().string());
                    Log.w(TAG,call.request().url().toString());
                    Log.w(TAG,call.request().headers().toString());
                    Log.w(TAG,call.request().method().toString());
//                    Log.w(TAG,call.request().body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void query(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fx.scnu.edu.cn/api/getpostlist.ashx/")
                .build();
        BlogService service = retrofit.create(BlogService.class);
        Call<ResponseBody> call = service.testFormUrlEncoded1(2);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.w(TAG,response.body().string());
                    Log.w(TAG,call.request().url().toString());
                    Log.w(TAG,call.request().headers().toString());
                    Log.w(TAG,call.request().method().toString());
//                    Log.w(TAG,call.request().body().s());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void query1(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fx.scnu.edu.cn/api/getpostlist.ashx/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BlogService service = retrofit.create(BlogService.class);
        Call<User> user1 = service.getUser1(2);
        user1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(call != null) {
                    Log.w(TAG,call.toString());
                }
                if(response != null) {
                    Log.w(TAG,response.toString());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }




}
