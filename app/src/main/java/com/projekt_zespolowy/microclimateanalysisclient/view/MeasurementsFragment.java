package com.projekt_zespolowy.microclimateanalysisclient.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMeasurementsBinding;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsViewModel;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.OptimizationDataViewModel;

import java.util.Calendar;

public class MeasurementsFragment extends Fragment implements Runnable {
    private static final String TAG = MeasurementsFragment.class.getName();

    private FragmentMeasurementsBinding binding;
    private MeasurementsViewModel measurementsViewModel;
    private OptimizationDataViewModel optimizationDataViewModel;
    private Handler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        handler = new Handler();
        measurementsViewModel = new ViewModelProvider(this).get(MeasurementsViewModel.class);
        measurementsViewModel.getSensors().observe(getViewLifecycleOwner(), sensors -> {
            // Set sensors readings values
            binding.textViewTimestampValue.setText(getTimestampDate(sensors.getTimestamp()));
            binding.textViewTemperatureValue.setText(String.valueOf(sensors.getTemperature()));
            binding.textViewHumidityValue.setText(String.valueOf(sensors.getHumidity()));
            binding.textViewPressureValue.setText(String.valueOf(sensors.getPressure()));
            binding.textViewGasValue.setText(gasToString(sensors.getGas()));

            // Set sensors readings colors
            binding.textViewTemperatureValue.setTextColor(getTemperatureColor(sensors.getTemperature()));
            binding.textViewGasValue.setTextColor(getGasColor(sensors.getGas()));
        });
        measurementsViewModel.getError().observe(getViewLifecycleOwner(), throwable -> {
            Snackbar.make(binding.getRoot(), throwable.toString(), Snackbar.LENGTH_SHORT).show();
        });

        optimizationDataViewModel = new ViewModelProvider(this).get(OptimizationDataViewModel.class);
        optimizationDataViewModel.getOptimizationData().observe(getViewLifecycleOwner(), optimizationData -> {
            // Set optimizationData readings values
            binding.textViewPeopleInTheRoomValue.setText(optimizationData.getPeopleInTheRoomAsString());
            binding.textViewWindowsOpenedValue.setText(optimizationData.getWindowsOpenedAsString());
        });
        optimizationDataViewModel.getError().observe(getViewLifecycleOwner(), throwable -> {
            Snackbar.make(binding.getRoot(),throwable.toString(),Snackbar.LENGTH_SHORT).show();
        });
        binding = FragmentMeasurementsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_measurements);
    }

    @Override
    public void run() {
        measurementsViewModel.updateSensors();
        optimizationDataViewModel.updateOptimizationData();
        handler.postDelayed(this, 5000);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(this);
    }

    private String getTimestampDate(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000);
        return DateFormat.format("HH:mm:ss dd-MM-yyyy", cal).toString();
    }

    private int getTemperatureColor(float temperature) {
        Resources res = getResources();

        if(temperature <= res.getInteger(R.integer.temperature_low_stop))
            return res.getColor(R.color.temperature_low);
        if(temperature >= res.getInteger(R.integer.temperature_medium_start) && temperature <= res.getInteger(R.integer.temperature_medium_stop))
            return res.getColor(R.color.temperature_medium);
        if(temperature >= res.getInteger(R.integer.temperature_high_start) && temperature <= res.getInteger(R.integer.temperature_high_stop))
            return res.getColor(R.color.temperature_high);

        return res.getColor(R.color.temperature_too_damn_high);
    }

    private String gasToString(float gas)
    {
        if(gas > 0 && gas < 51) return "Good";
        if(gas < 100) return "Average";
        if(gas < 150) return "Little bad";
        if(gas < 200) return "Bad";
        if(gas < 500) return "Very bad";
        else return "";
    }

    private int getGasColor(float gas)
    {
        Resources res = getResources();

        if(gas > 0 && gas < 51) return res.getColor(R.color.air_good);
        else if(gas < 100) return res.getColor(R.color.air_average);
        else if(gas < 150) return res.getColor(R.color.air_little_bad);
        else if(gas < 200) return res.getColor(R.color.air_bad);
        else if(gas < 500) return res.getColor(R.color.air_very_bad);
        else return 0;
    }

}
