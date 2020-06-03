package com.projekt_zespolowy.microclimateanalysisclient.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.projekt_zespolowy.microclimateanalysisclient.api.OptimizationDataApi;
import com.projekt_zespolowy.microclimateanalysisclient.model.OptimizationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OptimizationDataViewModel extends ViewModel {
    private OptimizationDataApi api;
    private MutableLiveData<OptimizationData> optimizationData;
    private MutableLiveData<Throwable> error;

    public OptimizationDataViewModel() {
        api = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OptimizationDataApi.class);
        optimizationData = new MutableLiveData<>();
        error = new MutableLiveData<>();
    }

    public LiveData<OptimizationData> getOptimizationData() {
        return optimizationData;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void updateOptimizationData() {
        api.optimizationData().enqueue(new Callback<OptimizationData>() {
            @Override
            public void onResponse(@NonNull Call<OptimizationData> call, @NonNull Response<OptimizationData> response) {
                optimizationData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OptimizationData> call, @NonNull Throwable t) {
                error.setValue(t);
            }
        });
    }
}
