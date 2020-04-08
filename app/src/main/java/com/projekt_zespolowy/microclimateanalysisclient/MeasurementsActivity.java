package com.projekt_zespolowy.microclimateanalysisclient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementsActivity extends AppCompatActivity implements Callback<Sensors>, Runnable {
    private final static int REFRESH_INTERVAL = 5000;

    private TextView textViewTemperatureValue;
    private TextView textViewHumidityValue;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);

        setTitle(R.string.measurements);

        //Setup toolbar aka actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Enable Up button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        textViewTemperatureValue = findViewById(R.id.textViewTemperatureValue);
        textViewHumidityValue = findViewById(R.id.textViewHumidityValue);

        handler = new Handler();
    }

    @Override
    public void run() {
        SensorsApi.getInstance().sensors().enqueue(this);
        handler.postDelayed(this, REFRESH_INTERVAL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(this);
    }

    @Override
    public void onResponse(Call<Sensors> call, Response<Sensors> response) {
        Sensors sensors = response.body();
        if (sensors != null) {
            textViewTemperatureValue.setText(String.valueOf(sensors.getTemperature()));
            textViewHumidityValue.setText(String.valueOf(sensors.getHumidity()));
        }
        Toast.makeText(this, "API ok", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Call<Sensors> call, Throwable t) {
        Toast.makeText(this, "API error", Toast.LENGTH_SHORT).show();
    }
}
