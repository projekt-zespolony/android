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
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_second);
        Snackbar.make(binding.getRoot(), getActivity().getTitle(), Snackbar.LENGTH_SHORT).show();
    }
}
