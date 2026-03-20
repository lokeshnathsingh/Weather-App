package com.company.weather.Models;

import java.util.List;

public class WeatherResponse {

    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private String name;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }

    public static class Main {
        private double temp;
        private double temp_min;
        private double temp_max;
        private int humidity;

        public double getTemp() { return temp; }
        public double getTempMin() { return temp_min; }
        public double getTempMax() { return temp_max; }
        public int getHumidity() { return humidity; }
    }

    public static class Weather {
        private String main;
        private String description;

        public String getMain() { return main; }
        public String getDescription() { return description; }
    }

    public static class Wind {
        private double speed;

        public double getSpeed() { return speed; }
    }
    public class Coord {
        private double lat;
        private double lon;

        public double getLat() { return lat; }
        public double getLon() { return lon; }
    }

    private Coord coord;

    public Coord getCoord() { return coord; }
}