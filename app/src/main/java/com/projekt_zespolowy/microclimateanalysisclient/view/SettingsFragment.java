package com.projekt_zespolowy.microclimateanalysisclient.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import com.projekt_zespolowy.microclimateanalysisclient.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = SettingsFragment.class.getName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        getActivity().setTitle(R.string.title_settings);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

