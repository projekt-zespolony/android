package com.projekt_zespolowy.microclimateanalysisclient.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMeasurementsFromDayBinding;
import com.projekt_zespolowy.microclimateanalysisclient.viewmodel.MeasurementsFromDayViewModel;

public class MeasurementsFromDayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MeasurementsFromDayFragment.class.getName();

    private FragmentMeasurementsFromDayBinding binding;
    private MeasurementsFromDayViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MeasurementsFromDayViewModel.class);
        viewModel.getSensorsHours().observe(getViewLifecycleOwner(), sensors -> {
            binding.swipeRefresh.setRefreshing(false);
            binding.countTextView.setText(String.valueOf(sensors.size()));
        });
        binding = FragmentMeasurementsFromDayBinding.inflate(inflater);
        binding.swipeRefresh.setColorSchemeResources(R.color.accent);
        binding.swipeRefresh.setOnRefreshListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_measurements_from_day);
        update();
    }

    private void update() {
        binding.swipeRefresh.setRefreshing(true);
        viewModel.updateSensorsHours(24);
    }

    @Override
    public void onRefresh() {
        update();
    }
}
