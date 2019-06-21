package com.barebones.retrofit;

import android.content.Context;

import com.barebones.constants.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit == null) {

            //Set Timeout to 1 min
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
