package com.zimincom.mafiaonline.remote;

import com.zimincom.mafiaonline.item.ResponseItem;
import com.zimincom.mafiaonline.item.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Zimincom on 2017. 3. 31..
 */

public interface MafiaRemoteService {

    String configAddress = "192.168.1.222:8080";
    String BASE_URL = "http://" + configAddress;
    String SOCKET_URL = "ws://" + configAddress + "/websockethandler/websocket";

    @POST("/api/login")
    Call<ResponseItem> sendLoginInput(@Body User user);

    @POST("/api/signup")
    Call<ResponseItem> sendSignUpInfo(@Body User user);

    @GET("/logout")
    Call<String> getLogout();

    @GET("/api/lobby")
    Call<ResponseItem> getRoomList();

    @FormUrlEncoded
    @POST("api/room")
    Call<ResponseItem> createNewRoom(@Field("title") String title);

    @GET("/api/room/{id}")
    Call<ResponseItem> enterRoom(@Path("id") String id);

    @DELETE("/api/room")
    Call<ResponseItem> leaveRoom(@Body User user);

}
