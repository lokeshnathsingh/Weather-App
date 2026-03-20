package com.company.weather.Domains;

public class FutureDomains {

    private String date;
    private String picPath;
    private String weather;
    private int maxTemp;
    private int minTemp;

    private int humidity;
    private int wind;
    private int rain;

    public FutureDomains(String date, String picPath, String weather,
                         int maxTemp, int minTemp,
                         int humidity, int wind, int rain) {

        this.date = date;
        this.picPath = picPath;
        this.weather = weather;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.humidity = humidity;
        this.wind = wind;
        this.rain = rain;
    }

    public int getHumidity() { return humidity; }
    public int getWind() { return wind; }
    public int getRain() { return rain; }

    public String getDate() { return date; }
    public String getPicPath() { return picPath; }
    public String getWeather() { return weather; }
    public int getMaxTemp() { return maxTemp; }
    public int getMinTemp() { return minTemp; }
}