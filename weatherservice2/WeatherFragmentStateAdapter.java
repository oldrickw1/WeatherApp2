package com.example.weatherservice2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherFragmentStateAdapter extends FragmentStateAdapter {

    List<WeatherModel> weatherModels;
    private final Context context;

    public WeatherFragmentStateAdapter(Context context, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<WeatherModel> weatherModels) {
        super(fragmentManager, lifecycle);
        this.weatherModels = weatherModels;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        WeatherCodeDetails details = WeatherMapping.getWeatherCodeDetails(context, weatherModels.get(position).getWeatherCode());
        args.putFloat(WeatherFragment.MAX_TEMP, weatherModels.get(position).getTempMax());
        args.putFloat(WeatherFragment.MIN_TEMP, weatherModels.get(position).getTempMin());
        args.putFloat(WeatherFragment.UV_INDEX, weatherModels.get(position).getUvIndex());
        args.putFloat(WeatherFragment.RAIN, weatherModels.get(position).getRainSum());
        args.putString(WeatherFragment.DESCRIPTION, details.getDescription());
        args.putString(WeatherFragment.IMG_URL, details.getUrlToIcon());
        args.putString(WeatherFragment.DATE, weatherModels.get(position).getDate());

        // test
        args.putSerializable("key", new ArrayList<>(Arrays.asList("a", "b", "c")));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return weatherModels.size();
    }
}
