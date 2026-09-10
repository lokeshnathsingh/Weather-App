# Weather-App

A location-based weather Android app built in Java, fetching current conditions and hourly/future forecasts from the OpenWeatherMap API.

## ✨ Features

- 📍 Auto-detects current location (Google Play Services Location)
- 🌤️ Current weather conditions (temperature, humidity, wind, etc.)
- ⏱️ Hourly forecast view
- 📅 Multi-day forecast screen (`FutureActivity`)
- 🔄 Pull-to-refresh (SwipeRefreshLayout)
- 🎨 Weather-condition icons (sunny, cloudy, rainy, snowy, storm)
- 🚀 Splash screen on launch

## 🛠️ Tech Stack

| Category | Tech |
|---|---|
| Language | Java |
| UI | XML layouts, RecyclerView, SwipeRefreshLayout |
| Location | Google Play Services Location |
| Networking | Retrofit2 + Gson converter |
| Image loading | Glide |
| Weather data | [OpenWeatherMap API](https://openweathermap.org/api) |
| Build system | Gradle (Kotlin DSL) |
| Min SDK / Target SDK | 24 / 36 |

## 📂 Project Structure

```
Weather-App/
├── app/
│   ├── build.gradle.kts                          # Dependencies: Retrofit, Glide, Play Services Location
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/company/weather/
│       │   ├── MainActivity.java                  # Current weather + hourly forecast screen
│       │   ├── FutureActivity.java                 # Multi-day forecast screen
│       │   ├── splashActivity.java                 # Launch screen
│       │   ├── ApiClient.java                      # Retrofit client (base URL: api.openweathermap.org)
│       │   ├── ApiInterface.java                   # Retrofit endpoints (current + forecast)
│       │   ├── Adapter/
│       │   │   ├── HourlyAdapter.java               # RecyclerView adapter for hourly forecast
│       │   │   └── FutureAdapter.java               # RecyclerView adapter for daily forecast
│       │   ├── Models/
│       │   │   ├── WeatherResponse.java             # Current weather API response model
│       │   │   └── ForecastResponse.java            # Forecast API response model
│       │   └── Domains/
│       │       ├── Hourly.java                      # Hourly forecast item (UI-bound)
│       │       └── FutureDomains.java                # Daily forecast item (UI-bound)
│       └── res/
│           ├── layout/          # activity_main, activity_future, activity_splash, hourly, viewholder_future
│           ├── drawable/        # Weather condition icons (sunny, rainy, cloudy, snowy, storm, etc.)
│           ├── font/            # Adlam Display font
│           └── values/          # Strings, colors, themes
├── gradle/
│   └── libs.versions.toml       # Version catalog
├── build.gradle.kts             # Top-level build config
└── settings.gradle.kts
```

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest stable)
- JDK 11+
- A free [OpenWeatherMap API key](https://openweathermap.org/api)
- Location permission granted on device/emulator

### Setup

```bash
git clone https://github.com/lokeshnathsingh/Weather-App.git
cd Weather-App
```

1. Open the project in Android Studio.
2. Add your OpenWeatherMap API key where `ApiInterface`/`MainActivity` calls the weather endpoints (better yet, move it into `local.properties` / `BuildConfig` instead of hardcoding it).
3. Sync Gradle.
4. Run on an emulator or physical device with location enabled.

### CLI Build

```bash
./gradlew assembleDebug
```

## 🗺️ Roadmap

- [ ] Manual city search (not just current location)
- [ ] Save favorite locations
- [ ] Widget for home screen
- [ ] Move API key out of source into `local.properties`

## 🤝 Contributing

Contributions, issues, and feature requests are welcome. Feel free to check the [issues page](https://github.com/lokeshnathsingh/Weather-App/issues).

## 📄 License

This project is open for educational use. Add a license file if you plan to distribute it publicly.

## 👤 Author

**Lokesh Nath Singh**
- GitHub: [@lokeshnathsingh](https://github.com/lokeshnathsingh)
- LinkedIn: [lokesh-nath-singh](https://linkedin.com/in/lokesh-nath-singh/)
