package com.gjayz.multimedia.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final int CONN_TIMEOUT = 20000;
    private static final int READ_TIMEOUT = 20000;
    private static final int WRITE_TIMEOUT = 20000;

    private OkHttpClient mOkHttpClient;

    private RetrofitManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private static volatile RetrofitManager sInstance;

    public static RetrofitManager getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitManager();
                }
            }
        }
        return sInstance;
    }

    public Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(mOkHttpClient)
                .build();
    }
}