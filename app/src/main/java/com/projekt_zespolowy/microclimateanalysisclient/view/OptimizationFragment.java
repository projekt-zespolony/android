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
import com.projekt_zespolowy.microclimateanalysisclient.model.OptimizationData;
import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsHistoryViewModel;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsViewModel;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.OptimizationDataViewModel;

import java.util.Calendar;
import java.util.Date;

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
        binding.textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
        updateViewModels();
    }

    private void updateViewModels()
    {
        measurementsViewModel.updateSensors();
        measurementsHistoryViewModel.updateSensorsHours(24);
        optimizationDataViewModel.updateOptimizationData();
    }

    private void updateView()
    {
        String text = "";
        updateViewModels();

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        boolean isWinter;
        if(month<5||month>9) isWinter=true;
        else isWinter=false;

        Sensors sensors = measurementsViewModel.getSensors().getValue();
        OptimizationData optimizationData = optimizationDataViewModel.getOptimizationData().getValue();

        float temperature = sensors.getTemperature();
        float humidity = sensors.getHumidity();
        float gas = sensors.getGas();
        boolean windowsAreOpened=false;

        if(optimizationData !=null)
        {
            windowsAreOpened=optimizationData.isWindows_are_opened();
        }

        if(anyMeasurementOutOfNorm(temperature,humidity, gas)) {
            text = "Values out of norm: \n\n";

            if (!(temperature > 18 && temperature < 27))
                text += "- temperature: " + temperature + "°C\n";
            if (!(humidity > 30 && humidity < 60))
                text += "- humidity: " + humidity + "%\n";
            if (gas > 60)
                text += "- air Quality: " + gasToString(gas) + "\n";

            text+="\n\n\n";
            text+="Recommendations:\n\n";
            if(temperature<18&&windowsAreOpened==true&&isWinter) text+="- close windows\n";
            if(temperature>28&&windowsAreOpened==false&&!isWinter) text+="- open windows or turn on air conditioning\n";
            if(humidity>60&&windowsAreOpened==false) text+="- open windows\n";
            if(humidity>70) text+="- consider using a moisture absorber or air dryer \n";
            if(gas>60) text+="- open windows\n";
            if(gas>150) text+="- consider using an air purifier";


        }
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

    private boolean anyMeasurementOutOfNorm(float temperature, float humidity, float gas)
    {
        Sensors sensors = measurementsViewModel.getSensors().getValue();

        if((sensors.getTemperature()>18 && sensors.getTemperature()<27) &&
                (sensors.getHumidity()>30&&sensors.getHumidity()<60) && !(sensors.getGas()>50)) return false;
        else return true;
    }

}
