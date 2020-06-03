package com.projekt_zespolowy.microclimateanalysisclient.view;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentOptimizationBinding;
import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsHistoryViewModel;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsViewModel;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.OptimizationDataViewModel;

public class OptimizationFragment extends Fragment {
    private FragmentOptimizationBinding binding;
    private MeasurementsHistoryViewModel measurementsHistoryViewModel;
    private MeasurementsViewModel measurementsViewModel;
    private OptimizationDataViewModel optimizationDataViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        measurementsHistoryViewModel = new ViewModelProvider(this).get(MeasurementsHistoryViewModel.class);

        optimizationDataViewModel = new ViewModelProvider(this).get(OptimizationDataViewModel.class);
        optimizationDataViewModel.getOptimizationData().observe(getViewLifecycleOwner(),optimizationData -> {
            updateView();
        });

        measurementsViewModel = new ViewModelProvider(this).get(MeasurementsViewModel.class);
        measurementsViewModel.getSensors().observe(getViewLifecycleOwner(),sensors -> {
            updateView();
        });

        binding = FragmentOptimizationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_optimization);
        updateViewModels();
        binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 13);
    }

    private void updateViewModels()
    {
        measurementsViewModel.updateSensors();
        measurementsHistoryViewModel.updateSensorsHours(24);
        optimizationDataViewModel.updateOptimizationData();
    }

    private void updateView()
    {
        String text;
        Sensors sensors = measurementsViewModel.getSensors().getValue();

        text = "Values out of norm: \n";
        if(!(sensors.getTemperature()>18 && sensors.getTemperature()<27)) text+="Temperature: " + sensors.getTemperature() + "°C\n";
        if(!(sensors.getHumidity()>30&&sensors.getHumidity()<60)) text+="Humidity: " +  sensors.getHumidity() + "%\n";
        if(sensors.getGas()>50) text+="Air Quality: "+ gasToString(sensors.getGas())+"\n";
        binding.textView.setText(text);
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

}
