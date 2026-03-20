package com.company.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.weather.Adapter.FutureAdapter;
import com.company.weather.Domains.FutureDomains;
import com.company.weather.Models.ForecastResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FutureActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterTomorrow;

    TextView dayText, tempText, statusText;
    TextView rainText, windText, humidityText;
    ImageView icon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        dayText = findViewById(R.id.dayText);
        tempText = findViewById(R.id.tempText);
        statusText = findViewById(R.id.statusText);
        icon = findViewById(R.id.statusPic);

        rainText = findViewById(R.id.rainText);
        windText = findViewById(R.id.windText);
        humidityText = findViewById(R.id.humidityText);

        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        double lat = getIntent().getDoubleExtra("lat", 0.0);
        double lon = getIntent().getDoubleExtra("lon", 0.0);

        getFutureWeather(lat, lon);
        setVariable();
    }

    private void getFutureWeather(double lat, double lon) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<ForecastResponse> call = api.get7DayForecast(
                lat,
                lon,
                "d4c74b50f29005ac80ceb9d79eb67f85",
                "metric"
        );

        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ArrayList<FutureDomains> items = new ArrayList<>();

                    for (int i = 0; i < response.body().getList().size(); i += 8) {

                        int minTemp = Integer.MAX_VALUE;
                        int maxTemp = Integer.MIN_VALUE;

                        int humidity = 0;
                        int wind = 0;
                        double rainChance = 0;

                        int count = 0;

                        for (int j = i; j < i + 8 && j < response.body().getList().size(); j++) {

                            int temp = (int) response.body().getList().get(j).getMain().getTemp();

                            if (temp < minTemp) minTemp = temp;
                            if (temp > maxTemp) maxTemp = temp;

                            humidity += response.body().getList().get(j).getMain().getHumidity();
                            wind += (int) response.body().getList().get(j).getWind().getSpeed();
                            rainChance += response.body().getList().get(j).getPop();

                            count++;
                        }

                        if (count == 0) continue;

                        humidity = humidity / count;
                        wind = wind / count;
                        rainChance = (rainChance / count) * 100;

                        String dateTime = response.body().getList().get(i).getDt_txt();
                        String day = dateTime.substring(0, 10);

                        String weatherMain = response.body().getList().get(i).getWeather().get(0).getMain();
                        String status = response.body().getList().get(i).getWeather().get(0).getDescription();

                        items.add(new FutureDomains(
                                day,
                                weatherMain,
                                status,
                                maxTemp,
                                minTemp,
                                humidity,
                                wind,
                                (int) rainChance
                        ));
                    }

                    adapterTomorrow = new FutureAdapter(items);
                    recyclerView.setAdapter(adapterTomorrow);

                    if (items.size() > 1) {

                        ForecastResponse.ListItem tomorrowRaw = response.body().getList().get(8);

                        int temp = (int) tomorrowRaw.getMain().getTemp();
                        int humidity = tomorrowRaw.getMain().getHumidity();
                        int wind = (int) (tomorrowRaw.getWind().getSpeed() * 3.6);
                        int rain = (int) (tomorrowRaw.getPop() * 100);

                        String weather = tomorrowRaw.getWeather().get(0).getMain();

                        rainText.setText(rain + "%");
                        windText.setText(wind + " kmph");
                        humidityText.setText(humidity + "%");

                        dayText.setText("Tomorrow");
                        tempText.setText(temp + "°");
                        statusText.setText(weather);

                        setWeatherIcon(weather);
                    }
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {

            }
        });
    }

    private void setWeatherIcon(String weather) {

        int imageRes;

        switch (weather.toLowerCase()) {

            case "clear":
                imageRes = R.drawable.sunny;
                break;

            case "clouds":
                imageRes = R.drawable.cloudy;
                break;

            case "rain":
            case "drizzle":
                imageRes = R.drawable.rainy;
                break;

            case "thunderstorm":
                imageRes = R.drawable.storm;
                break;

            case "snow":
                imageRes = R.drawable.snowy;
                break;

            default:
                imageRes = R.drawable.cloudy;
        }

        icon.setImageResource(imageRes);
    }

    private void setVariable() {
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }
}