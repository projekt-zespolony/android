package com.projekt_zespolowy.microclimateanalysisclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.projekt_zespolowy.microclimateanalysisclient.databinding.ActivityMainBinding;
import com.projekt_zespolowy.microclimateanalysisclient.firebase.Firebase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Return early if for example orientation changed
        if (savedInstanceState != null)
            return;

        String notificationsKey = getResources().getString(R.string.pref_key_notifications);
        boolean notificationsDefaultValue = getResources().getBoolean(R.bool.pref_notifications_default_value);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notifications = preferences.getBoolean(notificationsKey, notificationsDefaultValue);
        if (notifications)
            Firebase.subscribe();
        else
            Firebase.unsubscribe();
    }
}
