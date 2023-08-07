package com.example.weatherservice2;

public class WeatherModel {
    private CityData cityData;
    private int weatherCode;
    private float tempMax;
    private float tempMin;
    private float uvIndex;
    private float rainSum;
    private String date;

    public WeatherModel(CityData cityData, int weatherCode, float tempMax, float tempMin, float uvIndex, float rainSum) {
        this.cityData = cityData;
        this.weatherCode = weatherCode;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.uvIndex = uvIndex;
        this.rainSum = rainSum;
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
}