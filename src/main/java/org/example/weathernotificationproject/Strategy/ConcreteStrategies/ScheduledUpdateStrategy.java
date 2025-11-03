package org.example.weathernotificationproject.Strategy.ConcreteStrategies;

import org.example.weathernotificationproject.Observer.ConcreteSubject.WeatherStation;
import org.example.weathernotificationproject.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static java.lang.Math.clamp;
import static java.lang.Math.round;

@Component
public class ScheduledUpdateStrategy {
    private final Random random = new Random();
    @Autowired
    private WeatherStation station;

    @Autowired
    private APICurrentUpdateStrategy strategy;

    private String currentCity;


    public void setCity(String city) {
        this.currentCity = city;
        strategy.setCity(city);
        station.setUpdateStrategy(strategy);
    }

    @Scheduled(fixedRate = 2 * 1000)
    public void updateWeather() {
        if (currentCity != null) {
            System.out.println("Updating weather for " + currentCity);
            WeatherData data = strategy.update(currentCity);
            if (data != null) {
                data = applyRandom(data);
                station.fetchAndNotifyWithRandom(data);
            }
        }
    }
    private WeatherData applyRandom(WeatherData data) {
        String apiTime = data.getDate();
        LocalDateTime apiDateTime = LocalDateTime.parse(apiTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        int currentMinute = LocalDateTime.now().getMinute();
        LocalDateTime combinedTime = apiDateTime.withMinute(currentMinute);
        String newDate = combinedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        double temperature = clamp(data.getTemperature() + (random.nextDouble() * 2 - 1), -40, 40);
        double apparentTemperature = clamp(data.getApparentTemperature() + (random.nextDouble() * 2 - 1), -40, temperature);
        double humidity = clamp(data.getHumidity() + (random.nextDouble() * 2 - 1), 0, 100);
        double windSpeed = clamp(data.getWindSpeed() + (random.nextDouble() * 2 - 1), 0, 150);

        return new WeatherData.Builder()
                .city(data.getCity())
                .date(newDate)
                .temperature(round(temperature))
                .apparentTemperature(round(apparentTemperature))
                .humidity(round(humidity))
                .windSpeed(round(windSpeed))
                .precipitation(data.getPrecipitation())
                .rain(data.getRain())
                .weatherCode(data.getWeatherCode())
                .build();
    }

}
