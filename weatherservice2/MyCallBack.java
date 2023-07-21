package com.example.weatherservice2;

import java.util.concurrent.Callable;

public class MyCallBack implements VolleyResponseListener {

    @Override
    public void onError(String s) {

    }

    @Override
    public void onResponse(Object o) {
        Coordinate coordinate = (Coordinate) o;

    }
}
