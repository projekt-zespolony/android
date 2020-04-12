package com.projekt_zespolowy.microclimateanalysisclient.api;

import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SensorsApi {
    String BASE_URL = "https://projekt-zespolowy.pl";

    @GET("/sensors")
    Call<Sensors> sensors();
}
