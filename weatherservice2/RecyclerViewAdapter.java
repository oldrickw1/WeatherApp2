package com.example.weatherservice2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<WeatherModel> weatherModels;


    public RecyclerViewAdapter(ArrayList<WeatherModel> weatherModels) {
        this.weatherModels = weatherModels;
    }

    // Create ViewHolder class to hold references to the views of each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView maxTempTV;
        TextView minTempTV;
        TextView uVIndexTV;
        TextView rainTV;
        ImageView weatherImageIV;
        TextView dateTV;
        TextView descriptionTV;

        public ViewHolder(View itemView) {
            super(itemView);
            maxTempTV = itemView.findViewById(R.id.maxTempTV);
            minTempTV = itemView.findViewById(R.id.minTempTV);
            uVIndexTV = itemView.findViewById(R.id.uVIndexTV);
            rainTV = itemView.findViewById(R.id.rainTV);
            weatherImageIV = itemView.findViewById(R.id.imageView);
            dateTV = itemView.findViewById(R.id.dateTV);
            descriptionTV = itemView.findViewById(R.id.weatherDescriptionTV);
        }
    }

    // Inflate the item layout and return a new ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.weather_card, parent, false);
        return new ViewHolder(itemView);
    }

    // Bind data to the views in each item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        WeatherModel model =  weatherModels.get(position);
        WeatherCodeDetails details = WeatherMapping.getWeatherCodeDetails(context, model.getWeatherCode());

        holder.maxTempTV.setText("Max tmp: " + model.getTempMax());
        holder.minTempTV.setText("Min tmp: " + model.getTempMin());
        holder.uVIndexTV.setText("UV: " + model.getUvIndex());
        holder.rainTV.setText("Rain: " + model.getRainSum());
        holder.dateTV.setText(getDate(position));
        setWeathercodeIcon(holder, holder.itemView.getContext(), details.getUrlToIcon());
        holder.descriptionTV.setText(details.getDescription());
    }

    private void setWeathercodeIcon(ViewHolder holder, Context context, String urlToIcon) {
        try {
            Glide.with(context) // Pass the context from the holder.itemView
                    .load(urlToIcon)
                    .into(holder.weatherImageIV);
        } catch (Exception e) {
            Log.i("OLLIE", "onCreate: " + e.getMessage());
        }
    }

    private String getDate(int daysOffset) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(daysOffset);
        String pattern = "EEEE, dd MMMM";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        return targetDate.format(formatter);
    }

    // Return the number of items in the data set
    @Override
    public int getItemCount() {
        return weatherModels.size();
    }
}
