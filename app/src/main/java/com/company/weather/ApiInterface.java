
package com.company.weather;

import com.company.weather.Models.ForecastResponse;
import com.company.weather.Models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

    public interface ApiInterface {

        @GET("data/2.5/weather")
        Call<WeatherResponse> getWeatherByLatLon(
                @Query("lat") double lat,
                @Query("lon") double lon,
                @Query("appid") String apiKey,
                @Query("units") String units
        );

        @GET("data/2.5/weather")
        Call<WeatherResponse> getWeatherByCity(
                @Query("q") String city,
                @Query("appid") String apiKey,
                @Query("units") String units
        );
        @GET("data/2.5/forecast")
        Call<ForecastResponse> getHourlyWeather(
                @Query("lat") double lat,
                @Query("lon") double lon,
                @Query("appid") String apiKey,
                @Query("units") String units
        );
        @GET("data/2.5/forecast")
        Call<ForecastResponse> get7DayForecast(
                @Query("lat") double lat,
                @Query("lon") double lon,
                @Query("appid") String apiKey,
                @Query("units") String units
        );
    }
