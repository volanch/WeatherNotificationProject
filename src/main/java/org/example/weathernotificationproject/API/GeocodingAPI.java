package org.example.weathernotificationproject.API;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GeocodingAPI {

    @Autowired
    private RestTemplate restTemplate;


    public Coordinates getCoordinates(String city) {
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";

        String response = restTemplate.getForObject(geoUrl, String.class);
        JSONObject json = new JSONObject(response);
        JSONObject result = json.getJSONArray("results").getJSONObject(0);

        double latitude = result.getDouble("latitude");
        double longitude = result.getDouble("longitude");

        return new Coordinates(latitude, longitude);
    }

    @Getter
    public static class Coordinates {
        private final double latitude;
        private final double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
