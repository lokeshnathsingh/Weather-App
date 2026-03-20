package com.company.weather.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ForecastResponse {

    private List<ListItem> list;

    public List<ListItem> getList() {
        return list;
    }

    public static class ListItem {

        private Main main;
        private List<Weather> weather;
        private String dt_txt;

        private Wind wind;
        private Clouds clouds;
        private double pop;

        public Main getMain() {
            return main;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public Wind getWind() {
            return wind;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public double getPop() {
            return pop;
        }
    }

    public static class Main {

        private double temp;

        @SerializedName("temp_min")
        private double tempMin;

        @SerializedName("temp_max")
        private double tempMax;

        private int humidity;

        public double getTemp() {
            return temp;
        }

        public double getTempMin() {
            return tempMin;
        }

        public double getTempMax() {
            return tempMax;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class Weather {

        private String main;
        private String description;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }
    }
    public static class Wind {

        private double speed;

        public double getSpeed() {
            return speed;
        }
    }

    public static class Clouds {

        private int all;

        public int getAll() {
            return all;
        }
    }
}