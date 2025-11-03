package org.example.weathernotificationproject.Strategy.ConcreteStrategies;

import lombok.Setter;
import org.example.weathernotificationproject.API.GeocodingAPI;
import org.example.weathernotificationproject.Strategy.StrategyInterface.UpdateStrategy;
import org.example.weathernotificationproject.Strategy.StrategyInterface.UpdateStrategyWithLists;
import org.example.weathernotificationproject.WeatherData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class APIHourlyUpdateStrategy implements UpdateStrategyWithLists {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GeocodingAPI geocodingAPI;

    @Override
    public List<WeatherData> updateHourly(String city) {
        List<WeatherData> hourlyList = new ArrayList<>();
        try {
            GeocodingAPI.Coordinates coords = geocodingAPI.getCoordinates(city);

            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + coords.getLatitude() +
                    "&longitude=" + coords.getLongitude() +
                    "&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,wind_speed_10m," +
                    "precipitation,rain,weather_code&timezone=auto&forecast_days=1";

            String response = restTemplate.getForObject(url, String.class);
            JSONObject hourlyJson = new JSONObject(response).getJSONObject("hourly");

            JSONArray times = hourlyJson.getJSONArray("time");
            JSONArray temperatures = hourlyJson.getJSONArray("temperature_2m");
            JSONArray apparentTemps = hourlyJson.getJSONArray("apparent_temperature");
            JSONArray humidity = hourlyJson.getJSONArray("relative_humidity_2m");
            JSONArray windSpeeds = hourlyJson.getJSONArray("wind_speed_10m");
            JSONArray precipitation = hourlyJson.getJSONArray("precipitation");
            JSONArray rain = hourlyJson.getJSONArray("rain");
            JSONArray codes = hourlyJson.getJSONArray("weather_code");

            for (int i = 0; i < times.length(); i++) {
                WeatherData data = new WeatherData.Builder()
                        .city(city)
                        .date(times.getString(i))
                        .temperature(temperatures.getDouble(i))
                        .apparentTemperature(apparentTemps.getDouble(i))
                        .humidity(humidity.getDouble(i))
                        .windSpeed(windSpeeds.getDouble(i))
                        .precipitation(precipitation.getDouble(i))
                        .rain(rain.getDouble(i))
                        .weatherCode(String.valueOf(codes.getInt(i)))
                        .build();
                hourlyList.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hourlyList;
    }
}
