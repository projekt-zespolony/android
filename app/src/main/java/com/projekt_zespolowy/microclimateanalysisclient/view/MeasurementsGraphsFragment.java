package com.projekt_zespolowy.microclimateanalysisclient.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMeasurementsGraphsBinding;
import com.projekt_zespolowy.microclimateanalysisclient.model.Sensors;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello there, welcome.
 * Here is my spaghetti code.
 * Enjoy your meal.
 */

public class MeasurementsGraphsFragment extends Fragment {

    private FragmentMeasurementsGraphsBinding binding;
    private MeasurementsHistoryViewModel viewModel;
    private TabLayout tabLayout;

    private LineChart lineChart;
    private LineData lineData;
    private boolean firstDraw = false;

    // Temperature
    private List<Entry> entriesTemp = new ArrayList<>();
    private LineDataSet dataSetTemp;

    // Humidity
    private List<Entry> entriesHum = new ArrayList<>();
    private LineDataSet dataSetHum;

    // Pressure
    private List<Entry> entriesPre = new ArrayList<>();
    private LineDataSet dataSetPre;

    // Gas
    private List<Entry> entriesGas = new ArrayList<>();
    private LineDataSet dataSetGas;

    private class TabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    // Temperature
                    lineData = new LineData(dataSetTemp);
                    lineChart.getDescription().setText("Temperature from last 24 hours");
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                    break;
                case 1:
                    // Humidity
                    lineData = new LineData(dataSetHum);
                    lineChart.getDescription().setText("Humidity from last 24 hours");
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                    break;
                case 2:
                    // Pressure
                    lineData = new LineData(dataSetPre);
                    lineChart.getDescription().setText("Pressure from last 24 hours");
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                    break;
                case 3:
                    // Gas
                    lineData = new LineData(dataSetGas);
                    lineChart.getDescription().setText("Gas from last 24 hours");
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMeasurementsGraphsBinding.inflate(inflater);
        tabLayout = binding.tabs;
        tabLayout.addOnTabSelectedListener(new TabSelectedListener());

        initGraph();

        viewModel = new ViewModelProvider(this).get(MeasurementsHistoryViewModel.class);
        viewModel.getSensorsHours().observe(getViewLifecycleOwner(), new Observer<List<Sensors>>() {
            @Override
            public void onChanged(List<Sensors> sensors) {
                //logSensorsData(sensors);
                fetchDataForGraphs(sensors);
                firstDrawOfTemperatureGraph(sensors);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_measurements_graphs);
        viewModel.updateSensorsHours(24);
    }

    private void logSensorsData(List<Sensors> sensors) {
        for (Sensors s : sensors) {
            String temp = "Time: " + s.getTimestamp()
                    + " Temp: " + s.getTemperature()
                    + " Hum: " + s.getHumidity()
                    + " Press: " + s.getPressure()
                    + " Gas: " + s.getGas();
            Log.v("READINGS", temp);
        }
    }

    private void initGraph() {
        lineChart = binding.lineChart;
        lineChart.getDescription().setText("Temperature from last 24 hours");
        lineChart.getDescription().setTextSize(16f);
        lineChart.getDescription().setTextColor(Color.WHITE);
        lineChart.setGridBackgroundColor(Color.WHITE);
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.fitScreen();
    }

    private void firstDrawOfTemperatureGraph(List<Sensors> sensors) {
        if (firstDraw)
            return;

        LineData lineData = new LineData(dataSetTemp);
        lineChart.setData(lineData);
        lineChart.invalidate();
        firstDraw = true;
    }

    private void fetchDataForGraphs(List<Sensors> sensors) {
        // Temperature
        entriesTemp.clear();
        for (Sensors s : sensors) {
            entriesTemp.add(new Entry(s.getTimestamp(), s.getTemperature()));
        }
        dataSetTemp = new LineDataSet(entriesTemp, "Temperature [\u00B0 C]");
        dataSetTemp.setColor(Color.RED);
        dataSetTemp.setValueTextColor(Color.BLACK);
        dataSetTemp.setDrawCircles(false);

        // Humidity
        entriesHum.clear();
        for (Sensors s : sensors) {
            entriesHum.add(new Entry(s.getTimestamp(), s.getHumidity()));
        }
        dataSetHum = new LineDataSet(entriesHum, "Humidity [%]");
        dataSetHum.setColor(Color.BLUE);
        dataSetHum.setValueTextColor(Color.BLACK);
        dataSetHum.setDrawCircles(false);

        // Pressure
        entriesPre.clear();
        for (Sensors s : sensors) {
            entriesPre.add(new Entry(s.getTimestamp(), s.getPressure()));
        }
        dataSetPre = new LineDataSet(entriesPre, "Pressure [hPa]");
        dataSetPre.setColor(Color.LTGRAY);
        dataSetPre.setValueTextColor(Color.BLACK);
        dataSetPre.setDrawCircles(false);

        // Gas
        entriesGas.clear();
        for (Sensors s : sensors) {
            entriesGas.add(new Entry(s.getTimestamp(), s.getGas()));
        }
        dataSetGas = new LineDataSet(entriesGas, "Gas [kOhm]");
        dataSetGas.setColor(Color.YELLOW);
        dataSetGas.setValueTextColor(Color.BLACK);
        dataSetGas.setDrawCircles(false);
    }
}
