package com.company.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.weather.Domains.FutureDomains;
import com.company.weather.R;

import java.util.ArrayList;

public class FutureAdapter extends  RecyclerView.Adapter<FutureAdapter.ViewHolder>{

    ArrayList<FutureDomains> items;

    public FutureAdapter(ArrayList<FutureDomains> items) {
        this.items = items;
    }

    Context context;

    @NonNull
    @Override
    public FutureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_future,parent,false);
        context=parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureAdapter.ViewHolder holder, int position) {

        FutureDomains item = items.get(position);

        holder.dayTxt.setText(item.getDate());
        holder.statusTxt.setText(item.getWeather());
        holder.maxTxt.setText(item.getMaxTemp() + "°");
        holder.minTxt.setText(item.getMinTemp() + "°");

        int imageRes;

        switch (item.getPicPath().toLowerCase()) {

            case "clear":
                imageRes = R.drawable.sunny;
                break;

            case "clouds":
                imageRes = R.drawable.cloudy;
                break;

            case "rain":
                imageRes = R.drawable.rainy;
                break;

            case "snow":
                imageRes = R.drawable.snowy;
                break;

            case "drizzle":
                imageRes = R.drawable.rainy;
                break;

            case "thunderstorm":
                imageRes = R.drawable.storm;
                break;

            default:
                imageRes = R.drawable.cloudy;
                break;
        }

        holder.picP.setImageResource(imageRes);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayTxt, statusTxt, maxTxt, minTxt;
        ImageView picP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayTxt = itemView.findViewById(R.id.dayTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
            maxTxt = itemView.findViewById(R.id.maxTxt);
            minTxt = itemView.findViewById(R.id.minTxt);
            picP = itemView.findViewById(R.id.picP);
        }
    }
}
