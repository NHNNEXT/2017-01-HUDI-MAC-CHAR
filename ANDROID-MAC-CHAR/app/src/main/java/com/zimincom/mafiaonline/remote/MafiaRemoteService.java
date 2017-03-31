package com.zimincom.mafiaonline.remote;

import com.zimincom.mafiaonline.item.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Zimincom on 2017. 3. 31..
 */

public interface MafiaRemoteService {


    String BASE_URL = "http://mafia:8080";
    //String BASE_URL = "http://172.30.1.50:3000";
    //String BASE_URL = "http://192.168.1.222:8080";

    @POST("/api/login")
    Call<String> sendLoginInput(@Body User user);

    @POST("/api/signin")
    Call<String> sendSigninInfo(@Body String nickName, String email, String password);

    @GET("/logout")
    Call<String> getLogout(@Body  String email, String password);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();



}
