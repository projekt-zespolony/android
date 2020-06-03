package com.projekt_zespolowy.microclimateanalysisclient.model;

import android.content.res.Resources;
import com.projekt_zespolowy.microclimateanalysisclient.R;

public class Sensors {
    private final long timestamp;
    private final float temperature;
    private final float pressure;
    private final float humidity;
    private final float gas;

    public Sensors(long timestamp, float temperature, float pressure, float humidity, float gas) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.gas = gas;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getGas() {
        return gas;
    }


}
