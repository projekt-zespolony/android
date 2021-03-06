package com.projekt_zespolowy.microclimateanalysisclient.api;

import com.projekt_zespolowy.microclimateanalysisclient.model.OptimizationData;

import retrofit2.Call;
import retrofit2.http.GET;


public interface OptimizationDataApi {
    String BASE_URL = "http://projekt-zespolowy.pl";

    @GET("/optimization_data")
    Call<OptimizationData> optimizationData();
}
