package com.example.fieldmapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //private static final String BASE_URL = "http://5476ac37.ngrok.io/";
    //public static final String BASE_URL="https://apps.babbangona.com/mkt_app/field_mapping/";
    public static final String BASE_URL="http://332c1ede.ngrok.io/";
    private static Retrofit retrofit = null;

    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

}
