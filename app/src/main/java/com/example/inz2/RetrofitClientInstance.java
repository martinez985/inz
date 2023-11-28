package com.example.inz2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static String URL = "http://10.0.2.2:8086/";
    private static Retrofit retrofit;
    private static Gson gson;

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
