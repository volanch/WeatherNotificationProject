package org.example.weathernotificationproject.Strategy.ConcreteStrategies;

import lombok.Setter;
import org.example.weathernotificationproject.API.GeocodingAPI;
import org.example.weathernotificationproject.Strategy.StrategyInterface.UpdateStrategy;
import org.example.weathernotificationproject.WeatherData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class APICurrentUpdateStrategy implements UpdateStrategy {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GeocodingAPI geocodingAPI;

    @Setter
    private String city;

    @Override
    public WeatherData update(String city) {
        try{
            GeocodingAPI.Coordinates coords = geocodingAPI.getCoordinates(city);

            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + coords.getLatitude() +
                    "&longitude=" + coords.getLongitude() +
                    "&current=temperature_2m,apparent_temperature,relative_humidity_2m,wind_speed_10m,precipitation,rain,weather_code" +
                    "&timezone=auto";

            String weatherResponse = restTemplate.getForObject(url, String.class);
            JSONObject weatherJson = new JSONObject(weatherResponse).getJSONObject("current");

            return new WeatherData.Builder()
                    .city(city)
                    .date(weatherJson.getString("time"))
                    .temperature(weatherJson.getDouble("temperature_2m"))
                    .apparentTemperature(weatherJson.getDouble("apparent_temperature"))
                    .humidity(weatherJson.getDouble("relative_humidity_2m"))
                    .windSpeed(weatherJson.getDouble("wind_speed_10m"))
                    .precipitation(weatherJson.getDouble("precipitation"))
                    .rain(weatherJson.getDouble("rain"))
                    .weatherCode(weatherJson.get("weather_code").toString())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}