package com.company.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import com.company.weather.Models.ForecastResponse;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.company.weather.Adapter.HourlyAdapter;
import com.company.weather.Domains.Hourly;
import com.company.weather.Models.WeatherResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST = 1;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    private FusedLocationProviderClient fusedLocationClient;
    double currentLat = 0.0;
    double currentLon = 0.0;

    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefresh;

    public TextView tempText, statusText,rainText, cityText, maxMinText, humidityText, windText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        tempText = findViewById(R.id.textView3);
        statusText = findViewById(R.id.textView);
        cityText = findViewById(R.id.textView2);
        maxMinText = findViewById(R.id.textView4);
        rainText = findViewById(R.id.rainText);
        humidityText = findViewById(R.id.humidityText);
        windText = findViewById(R.id.windText);
        progressBar = findViewById(R.id.progressBar);

        checkLocationPermission();

        swipeRefresh = findViewById(R.id.swipeRefresh);

        swipeRefresh.setOnRefreshListener(() -> {
            checkLocationPermission();
        });

        initRecyclerView();
        setVariable();
    }

    private void checkLocationPermission() {

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST);

        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        progressBar.setVisibility(View.VISIBLE);

        fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
        ).addOnSuccessListener(location -> {

            if (location != null) {
                getWeatherByLocation(location.getLatitude(), location.getLongitude());
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    private void getWeatherByLocation(double lat, double lon) {
        currentLat = lat;
        currentLon = lon;

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = api.getWeatherByLatLon(
                lat, lon,
                "d4c74b50f29005ac80ceb9d79eb67f85",
                "metric"
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {

                    WeatherResponse data = response.body();
                    updateUI(data);

                    getHourlyData(lat, lon);

                } else {
                    Toast.makeText(MainActivity.this, "API Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getWeatherByCity(String cityName) {

        progressBar.setVisibility(View.VISIBLE);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = api.getWeatherByCity(
                cityName,
                "d4c74b50f29005ac80ceb9d79eb67f85",
                "metric"
        );

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {

                    WeatherResponse data = response.body();
                    currentLat = data.getCoord().getLat();
                    currentLon = data.getCoord().getLon();

                    updateUI(data);

                    getHourlyData(currentLat, currentLon);

                } else {
                    Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setVariable() {

        TextView nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, FutureActivity.class);

            intent.putExtra("lat", currentLat);
            intent.putExtra("lon", currentLon);

            startActivity(intent);
        });

        EditText searchEditText = findViewById(R.id.searchEditText);
        ImageView searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(v -> {

            String city = searchEditText.getText().toString().trim();

            if (!city.isEmpty()) {
                getWeatherByCity(city);
            } else {
                Toast.makeText(this, "Enter city name", Toast.LENGTH_SHORT).show();
            }
        });
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {

            String city = searchEditText.getText().toString().trim();

            if (!city.isEmpty()) {
                getWeatherByCity(city);
            }

            return true;
        });
    }

    private void getHourlyData(double lat, double lon) {

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

        Call<ForecastResponse> call = api.getHourlyWeather(
                lat,
                lon,
                "d4c74b50f29005ac80ceb9d79eb67f85",
                "metric"
        );

        call.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ArrayList<Hourly> items = new ArrayList<>();

                    for (int i = 0; i < 8; i++) {

                        String fullTime = response.body().getList().get(i).getDt_txt();
                        String hour = fullTime.substring(11, 13);
                        int h = Integer.parseInt(hour);

                        String formattedTime = (h > 12 ? (h - 12) : h) + " " + (h >= 12 ? "PM" : "AM");

                        int temp = (int) response.body().getList().get(i).getMain().getTemp();
                        String icon = response.body().getList().get(i).getWeather().get(0).getMain();

                        items.add(new Hourly(formattedTime, icon, temp));
                    }

                    mAdapter = new HourlyAdapter(items);
                    recyclerView.setAdapter(mAdapter);

                    double maxPop = 0;

                    for (int i = 0; i < response.body().getList().size(); i++) {

                        double pop = response.body().getList().get(i).getPop();

                        if (pop > maxPop) {
                            maxPop = pop;
                        }
                    }

                    int rainPercent = (int) (maxPop * 100);
                    rainText.setText(rainPercent + "%");
                }
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {

            }
        });
    }
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.RecyclerView1);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        mAdapter = new HourlyAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
    }
    private void updateUI(WeatherResponse data) {

        tempText.setText((int) data.getMain().getTemp() + "°");

        String status = data.getWeather().get(0).getDescription();
        statusText.setText(status.toUpperCase());

        cityText.setText(data.getName());

        maxMinText.setText("H:" + (int) data.getMain().getTempMax()
                + " L:" + (int) data.getMain().getTempMin());

        humidityText.setText(data.getMain().getHumidity() + "%");

        int windSpeed = (int) (data.getWind().getSpeed() * 3.6);
        windText.setText(windSpeed + " km/h");
    }
}