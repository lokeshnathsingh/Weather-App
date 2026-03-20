package com.company.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.company.weather.Domains.Hourly;
import com.company.weather.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HourlyAdapter extends  RecyclerView.Adapter<HourlyAdapter.ViewHolder>{

    ArrayList<Hourly> items;

    public HourlyAdapter(ArrayList<Hourly> items) {
        this.items = items;
    }

    Context context;

    @NonNull
    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly,parent,false);
        context=parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.ViewHolder holder, int position) {

        Hourly item = items.get(position);

        holder.hourTxt.setText(item.getHour());
        holder.tempTxt.setText(item.getTemp() + "°");

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

        holder.pic.setImageResource(imageRes);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourTxt, tempTxt;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            hourTxt = itemView.findViewById(R.id.hourTxt);
            tempTxt = itemView.findViewById(R.id.tempTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
