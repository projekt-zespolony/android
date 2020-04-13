package com.projekt_zespolowy.microclimateanalysisclient.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.projekt_zespolowy.microclimateanalysisclient.api.SensorsApi;
import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeasurementsViewModel extends ViewModel implements Callback<Sensors> {
    private SensorsApi api;
    private MutableLiveData<Sensors> sensors;
    private MutableLiveData<Throwable> error;

    public MeasurementsViewModel() {
        api = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SensorsApi.class);
        sensors = new MutableLiveData<>();
        error = new MutableLiveData<>();
    }

    public LiveData<Sensors> getSensors() {
        return sensors;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void updateSensors() {
        api.sensors().enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<Sensors> call, @NonNull Response<Sensors> response) {
        sensors.setValue(response.body());
    }

    @Override
    public void onFailure(@NonNull Call<Sensors> call, @NonNull Throwable t) {
        error.setValue(t);
    }
}
