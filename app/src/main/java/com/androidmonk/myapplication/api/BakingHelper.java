package com.androidmonk.myapplication.api;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingHelper {
    private static BakingApi bakingApiService;

    public static synchronized BakingApi getInstance(Context context) {
        if(bakingApiService== null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            bakingApiService = retrofit.create(BakingApi.class);
        }

        return bakingApiService;
    }
}
