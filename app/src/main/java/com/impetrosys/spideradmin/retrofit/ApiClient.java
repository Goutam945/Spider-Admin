package com.impetrosys.spideradmin.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String ROOT_URL = "https://impetrosys.com/spiderapp/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



    public static Retrofit getCustomClient(String baseURL) {

        Retrofit retrofit_ = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit_;
    }

    public static OkHttpClient okHttpClient = null;

    public static OkHttpClient getRequestHeader() {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
