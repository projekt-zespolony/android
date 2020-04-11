package com.projekt_zespolowy.microclimateanalysisclient;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SensorsApi {
    private static final String BASE_URL = "https://projekt-zespolowy.pl";

    private static SensorsApi instance;
    private Retrofit retrofit;

    public SensorsApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static SensorsApi getInstance() {
        if (instance == null)
            instance = new SensorsApi();

        return instance;

    }

    public static Call<Sensors> getSensorsCall() {
        return getInstance().getRetrofit().create(SensorsInterface.class).sensors();
    }
}

