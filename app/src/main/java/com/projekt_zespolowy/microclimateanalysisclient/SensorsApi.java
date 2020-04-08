package com.projekt_zespolowy.microclimateanalysisclient;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class SensorsApi {
    private static final String BASE_URL = "https://projekt-zespolony.awsmppl.com";

    private static Retrofit retrofit;

    public static SensorsInterface getInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit.create(SensorsInterface.class);
    }
}

