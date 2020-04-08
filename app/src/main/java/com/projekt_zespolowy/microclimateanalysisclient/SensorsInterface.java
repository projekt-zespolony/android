package com.projekt_zespolowy.microclimateanalysisclient;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SensorsInterface {
    @GET("/sensors")
    Call<Sensors> sensors();
}
