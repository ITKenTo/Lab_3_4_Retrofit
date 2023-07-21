package com.example.ph26746_lab_3_4;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static final  String BaseUrl= "http://192.168.1.15:3000/";

    public static ApiService getService(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);

    }
}
