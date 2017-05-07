package com.zimincom.mafiaonline.remote;

import com.facebook.stetho.okhttp3.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieHandler;
import java.net.CookieManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zimincom on 2016. 11. 29..
 */

public class ServiceGenerator {

    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        CookieHandler cookieHandler = new CookieManager();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()

                .addInterceptor(logging)
                .addNetworkInterceptor(new StethoInterceptor());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retorfit = new Retrofit.Builder()
                .baseUrl(MafiaRemoteService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        return retorfit.create(serviceClass);
    }
}
