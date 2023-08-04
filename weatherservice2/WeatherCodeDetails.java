package com.example.weatherservice2;

public class WeatherCodeDetails {
    private String description;
    private String urlToIcon;

    public WeatherCodeDetails(String description, String urlToIcon) {
        this.description = description;
        this.urlToIcon = urlToIcon;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToIcon() {
        return urlToIcon;
    }
}
