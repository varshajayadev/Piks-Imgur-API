package com.varshajayadev.piks;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by varshajayadev on Networking/24/17.
 */

public class ApiClient {

    private static Retrofit retrofit;
    public static int page_Count = 0;
    private ApiClient(){

    }
    public static Retrofit getClient(String Base_URL) {
            //---Logging
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            //Logging----

            retrofit = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        return retrofit;
    }

}
