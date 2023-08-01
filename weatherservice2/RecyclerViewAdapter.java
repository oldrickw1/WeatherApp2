package com.example.weatherservice2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

        public ViewHolder(View itemView) {
            super(itemView);
            maxTempTV = itemView.findViewById(R.id.maxTempTV);
            minTempTV = itemView.findViewById(R.id.minTempTV);
            uVIndexTV = itemView.findViewById(R.id.uVIndexTV);
            rainTV = itemView.findViewById(R.id.rainTV);
            weatherImageIV = itemView.findViewById(R.id.imageView);
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
        WeatherModel model =  weatherModels.get(position);

        holder.maxTempTV.setText("Max tmp: " + model.getTempMax());
        holder.minTempTV.setText("Min tmp: " + model.getTempMin());
        holder.uVIndexTV.setText("UV: " + model.getUvIndex());
        holder.rainTV.setText("Rain: " + model.getRainSum());
    }

    // Return the number of items in the data set
    @Override
    public int getItemCount() {
        return weatherModels.size();
    }
}
