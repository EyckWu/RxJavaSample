package com.eyck.rxjavademo.test_demo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Eyck on 2017/8/31.
 */

public interface BlogService {
    @GET("user/{id}/repos")//https://fx.scnu.edu.cn/api/getpostlist.ashx/user/2/repos
    Call<ResponseBody> getBlog(@Path("id") String id);


    @GET("users/list")//https://fx.scnu.edu.cn/api/getpostlist.ashx/users/list
    Call<ResponseBody> getBlog1();

    @GET("users/list?sort=desc")//https://fx.scnu.edu.cn/api/getpostlist.ashx/users/list?sort=desc
    Call<ResponseBody> getBlog2();

    @GET("group/{id}/users")//https://fx.scnu.edu.cn/api/getpostlist.ashx/group/2/users
    Call<ResponseBody> getBlog3(@Path("id") int id);

    @GET("group/{id}/users")//https://fx.scnu.edu.cn/api/getpostlist.ashx/group/2/users?sort=eyck
    Call<ResponseBody> getBlog4(@Path("id") int groupId, @Query("sort") String sort);

    @GET(".")
    Call<ResponseBody> testFormUrlEncoded1(@Query("pid") int pid);

    @POST("users/new")
    Call<ResponseBody> createUser(@Body User user);

    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);

    @GET(".")//https://fx.scnu.edu.cn/api/getpostlist.ashx/users/list
    Call<User> getUser1(@Query("pid") int pid);

    @GET("/user")
    public void getUser(@Query("userId") String userId, Callback<User> callback);


}
