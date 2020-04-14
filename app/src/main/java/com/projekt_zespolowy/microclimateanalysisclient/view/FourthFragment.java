package com.projekt_zespolowy.microclimateanalysisclient.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentFourthBinding;

public class FourthFragment extends Fragment {
    private FragmentFourthBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFourthBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_fourth);
        Snackbar snackbar = Snackbar.make(binding.getRoot(), getActivity().getTitle(), Snackbar.LENGTH_LONG);
        snackbar.setAction("BACK", v -> {
            Navigation.findNavController(getActivity(), R.id.navigation_host_fragment).navigateUp();
        });
        snackbar.show();
    }
}
