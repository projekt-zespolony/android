package com.projekt_zespolowy.microclimateanalysisclient.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMeasurementsBinding;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsViewModel;

public class MeasurementsFragment extends Fragment implements Runnable {
    private static final String TAG = MeasurementsFragment.class.getName();

    private FragmentMeasurementsBinding binding;
    private MeasurementsViewModel viewModel;
    private Handler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        handler = new Handler();
        viewModel = new ViewModelProvider(this).get(MeasurementsViewModel.class);
        viewModel.getSensors().observe(getViewLifecycleOwner(), sensors -> {
            // Set sensors readings values
            binding.textViewTemperatureValue.setText(String.valueOf(sensors.getTemperature()));
            binding.textViewHumidityValue.setText(String.valueOf(sensors.getHumidity()));
            binding.textViewPressureValue.setText(String.valueOf(sensors.getPressure()));
            binding.textViewGasValue.setText(String.valueOf(sensors.getGas()));

            // Set sensors readings colors
            binding.textViewTemperatureValue.setTextColor(getTemperatureColor(sensors.getTemperature()));
        });
        viewModel.getError().observe(getViewLifecycleOwner(), throwable -> {
            Snackbar.make(binding.getRoot(), throwable.toString(), Snackbar.LENGTH_SHORT).show();
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
        viewModel.updateSensors();
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
}
