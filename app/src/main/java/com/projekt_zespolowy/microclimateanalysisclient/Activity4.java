package com.projekt_zespolowy.microclimateanalysisclient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.projekt_zespolowy.microclimateanalysisclient.databinding.Activity4Binding;

public class Activity4 extends AppCompatActivity {
    private Activity4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO Change name.
        setTitle("Activity 4");

        //Setup toolbar aka actionbar
        setSupportActionBar(binding.toolbar);
        //Enable Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
