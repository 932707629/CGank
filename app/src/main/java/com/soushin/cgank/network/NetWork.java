package com.soushin.cgank.network;

import com.blankj.ALog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soushin.cgank.Configure;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/3.
 */

public class NetWork {
    private static CGankApi api;
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static final int DEFAULT_TIMEOUT = 20;
    private static Gson gson = new GsonBuilder().setLenient().create();
    public static CGankApi getCGankApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getokHttpClient())
                    .baseUrl(Configure.ip)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            api = retrofit.create(CGankApi.class);
        }
        return api;
    }

    private static OkHttpClient getokHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                ALog.e(message);
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
