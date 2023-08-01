package com.example.weatherservice2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteCityAdapter extends ArrayAdapter<CityData> {
    private List<CityData> cityListFull;
    public AutoCompleteCityAdapter(@NonNull Context context, @NonNull List<CityData> cityDataList) {
        super(context, 0, cityDataList);
        cityListFull = new ArrayList<>(cityDataList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return cityFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.city_autocomplete_row, parent, false
            );
        }

        TextView cityTextView = convertView.findViewById(R.id.city_name);
        TextView countryTextView = convertView.findViewById(R.id.city_country);

        CityData cityData = getItem(position);

        if (cityData != null) {
            cityTextView.setText(cityData.getCityName());
            countryTextView.setText(cityData.getCountry());
        }

        return convertView;
    }

    private Filter cityFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<CityData> suggestions = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(cityListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (CityData city: cityListFull) {
                    if (city.getCityName().toLowerCase().startsWith(filterPattern)) {
                        suggestions.add(city);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((CityData) resultValue).getCityName();
        }
    };
}
