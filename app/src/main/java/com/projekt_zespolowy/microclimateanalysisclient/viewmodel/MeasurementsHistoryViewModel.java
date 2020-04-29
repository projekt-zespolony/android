package com.projekt_zespolowy.microclimateanalysisclient.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.projekt_zespolowy.microclimateanalysisclient.api.SensorsApi;
import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeasurementsHistoryViewModel extends ViewModel {
    private SensorsApi api;
    private MutableLiveData<List<Sensors>> sensorsHours;
    private MutableLiveData<Throwable> error;

    public MeasurementsHistoryViewModel() {
        api = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SensorsApi.class);
        sensorsHours = new MutableLiveData<>();
        error = new MutableLiveData<>();
    }

    public LiveData<List<Sensors>> getSensorsHours() {
        return sensorsHours;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void updateSensorsHours(int hours) {
        api.sensorsHours(hours).enqueue(new Callback<List<Sensors>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sensors>> call, @NonNull Response<List<Sensors>> response) {
                sensorsHours.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Sensors>> call, @NonNull Throwable t) {
                error.setValue(t);
            }
        });
    }
}

