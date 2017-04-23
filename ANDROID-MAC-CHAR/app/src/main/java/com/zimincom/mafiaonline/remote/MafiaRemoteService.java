package com.zimincom.mafiaonline.remote;

import com.zimincom.mafiaonline.item.ResponseItem;
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


    String BASE_URL = "http://211.249.60.54:8000";
    //String BASE_URL = "http://1.255.56.109:8080";
    //String BASE_URL = "http://172.30.1.50:3000";
    //String BASE_URL = "http://192.168.1.222:8080";

    @POST("/api/login")
    Call<ResponseItem> sendLoginInput(@Body User user);

    @POST("/api/signup")
    Call<ResponseItem> sendSignUpInfo(@Body User user);

    @GET("/logout")
    Call<String> getLogout();

    @GET("/api/lobby")
    Call<ResponseItem> getRoomList();


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
