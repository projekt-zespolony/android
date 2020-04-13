package com.projekt_zespolowy.microclimateanalysisclient.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.projekt_zespolowy.microclimateanalysisclient.R;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getName();

    private FragmentMainBinding binding;
    private NavController navigation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        navigation = Navigation.findNavController(getActivity(), R.id.navigation_host_fragment);
        binding = FragmentMainBinding.inflate(inflater);
        binding.imageButton1.setOnClickListener(v -> {
            navigation.navigate(R.id.action_mainFragment_to_measurementsFragment);
        });
        binding.imageButton2.setOnClickListener(v -> {
            navigation.navigate(R.id.action_mainFragment_to_secondFragment);
        });
        binding.imageButton3.setOnClickListener(v -> {
            navigation.navigate(R.id.action_mainFragment_to_thirdFragment);
        });
        binding.imageButton4.setOnClickListener(v -> {
            navigation.navigate(R.id.action_mainFragment_to_fourthFragment);
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_main);

        // Show options on action bar
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemSettings:
                navigation.navigate(R.id.action_mainFragment_to_settingsFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
