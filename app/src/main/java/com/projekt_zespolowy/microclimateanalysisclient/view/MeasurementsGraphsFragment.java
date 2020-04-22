package com.projekt_zespolowy.microclimateanalysisclient.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMeasurementsGraphsBinding;

public class MeasurementsGraphsFragment extends Fragment {
    private FragmentMeasurementsGraphsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMeasurementsGraphsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_measurements_graphs);
        Snackbar.make(binding.getRoot(), getActivity().getTitle(), Snackbar.LENGTH_SHORT).show();
    }
}
