package com.example.weatherservice2;

public class CityData {
    private String cityName;
    private String country;
    private double longitude;
    private double latitude;

    public CityData(String cityName, String country, double longitude, double latitude) {
        this.cityName = cityName;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
