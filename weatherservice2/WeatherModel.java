package com.example.weatherservice2;

import android.util.Log;

import java.util.List;

public class WeatherModel {
    private final CityData cityData;
    private final int weatherCode;
    private final float tempMax;
    private final float tempMin;
    private final float uvIndex;
    private final float rainSum;
    private String date;
    private List<TempDP> dailyTemperatureDataPoints;


    public WeatherModel(CityData cityData, int weatherCode, float tempMax, float tempMin, float uvIndex, float rainSum, List<TempDP> dailyTemperatureDataPoints) {
        this.cityData = cityData;
        this.weatherCode = weatherCode;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.uvIndex = uvIndex;
        this.rainSum = rainSum;
        this.dailyTemperatureDataPoints = dailyTemperatureDataPoints;
    }

    public List<TempDP> getDailyTemperatureDataPoints() {
        return dailyTemperatureDataPoints;
    }

    public CityData getCityData() {
        return cityData;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public float getTempMax() {
        return tempMax;
    }


    public float getTempMin() {
        return tempMin;
    }


    public float getUvIndex() {
        return uvIndex;
    }


    public float getRainSum() {
        return rainSum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "cityData=" + cityData +
                ", weatherCode=" + weatherCode +
                ", tempMax=" + tempMax +
                ", tempMin=" + tempMin +
                ", uvIndex=" + uvIndex +
                ", rainSum=" + rainSum +
                '}';
    }

    public static class TempDP {
        private int time;
        private double temperature;

        public TempDP(int hour, double temperature) {
            Log.d("DEBUG", "TemperatureDataPoint: hour: " + hour + ",temperature: " + temperature);
            this.time = hour;
            this.temperature = temperature;
        }

        public int getTime() {
            return time;
        }

        public double getTemperature() {
            return temperature;
        }

        @Override
        public String toString() {
            return "TemperatureDataPoint{" +
                    "time='" + time + '\'' +
                    ", temperature=" + temperature +
                    '}';
        }
    }
}