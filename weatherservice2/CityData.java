package com.example.weatherservice2;

public class CityData {
    private String cityName;
    private String country;
    private double latitude;
    private double longitude;

    public CityData(String cityName, String country, double latitude, double longitude) {
        this.cityName = cityName;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return "CityData{" +
                "cityName='" + cityName + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
