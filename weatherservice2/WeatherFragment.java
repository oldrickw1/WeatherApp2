package com.example.weatherservice2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class WeatherFragment extends Fragment {
    public static final String MAX_TEMP = "maxTemp";
    public static final String MIN_TEMP = "minTemp";
    public static final String IMG_URL = "imgURL";
    public static final String UV_INDEX = "uVIndex";
    public static final String RAIN = "rain";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.maxTempTV)).setText("Max temp: " + getArguments().getFloat(MAX_TEMP));
        ((TextView) view.findViewById(R.id.minTempTV)).setText("Min temp: " + getArguments().getFloat(MIN_TEMP));
        ((TextView) view.findViewById(R.id.uVIndexTV)).setText("UV index: " + getArguments().getFloat(UV_INDEX));
        ((TextView) view.findViewById(R.id.rainTV)).setText("Rain: " + getArguments().getFloat(RAIN));
        ((TextView) view.findViewById(R.id.weatherDescriptionTV)).setText(getArguments().getString(DESCRIPTION));
        ((TextView) view.findViewById(R.id.dateTV)).setText(getArguments().getString(DATE));
        setWeathercodeIcon(view, getContext(), getArguments().getString(IMG_URL));

    }

    private void setWeathercodeIcon(View view, Context context, String urlToIcon) {
        try {
            Glide.with(context)
                    .load(urlToIcon)
                    .into((ImageView) view.findViewById(R.id.weatherIcon));
        } catch (Exception e) {
            Log.i("OLLIE", "onCreate: " + e.getMessage());
        }
    }
}