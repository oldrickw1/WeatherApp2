package com.example.weatherservice2;

import androidx.annotation.NonNull;

public class WeatherModel {
    private float tempMax;
    private float tempMin;
    private float uvIndex;
    private float rainSum;

    public WeatherModel(float tempMax, float tempMin, float uvIndex, float rainSum) {
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.uvIndex = uvIndex;
        this.rainSum = rainSum;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(float uvIndex) {
        this.uvIndex = uvIndex;
    }

    public float getRainSum() {
        return rainSum;
    }

    public void setRainSum(float rainSum) {
        this.rainSum = rainSum;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", uvIndex=" + uvIndex +
                ", rainSum=" + rainSum +
                '}';
    }
}