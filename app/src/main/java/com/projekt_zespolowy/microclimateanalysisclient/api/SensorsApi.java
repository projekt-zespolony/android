package com.projekt_zespolowy.microclimateanalysisclient.api;

import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SensorsApi {
    String BASE_URL = "https://projekt-zespolowy.pl";

    @GET("/sensors")
    Call<Sensors> sensors();
    @GET("/sensors/{hours}")
    Call<List<Sensors>> sensorsHours(@Path("hours") int hours);
}
