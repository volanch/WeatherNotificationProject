package org.example.weathernotificationproject;


import org.example.weathernotificationproject.Observer.ConcreteSubject.WeatherStation;
import org.example.weathernotificationproject.Strategy.ConcreteStrategies.APICurrentUpdateStrategy;
import org.example.weathernotificationproject.Strategy.ConcreteStrategies.APIHourlyUpdateStrategy;
import org.example.weathernotificationproject.Strategy.ConcreteStrategies.ScheduledUpdateStrategy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final APICurrentUpdateStrategy currentStrategy;
    private final APIHourlyUpdateStrategy hourlyStrategy;
    private final WeatherStation weatherStation;
    private final ScheduledUpdateStrategy scheduler;

    public WeatherController(APICurrentUpdateStrategy currentStrategy,
                             APIHourlyUpdateStrategy hourlyStrategy,
                             WeatherStation weatherStation,
                             ScheduledUpdateStrategy scheduler) {
        this.currentStrategy = currentStrategy;
        this.hourlyStrategy = hourlyStrategy;
        this.weatherStation = weatherStation;
        this.scheduler = scheduler;
    }

    @GetMapping("/{city}")
    public WeatherData getWeather(@PathVariable String city) {
        scheduler.setCity(city);
        WeatherData data = currentStrategy.update(city);
        if (data != null) {
            weatherStation.fetchAndNotify(city);
            return data;
        } else {
            throw new RuntimeException("Failed to fetch weather data for " + city);
        }
    }

    @GetMapping("/hourly/{city}")
    public List<WeatherData> getHourlyWeather(@PathVariable String city) {
        List<WeatherData> hourlyData = hourlyStrategy.updateHourly(city);
        if (hourlyData != null) {
            weatherStation.fetchAndNotifyHourly(hourlyData);
            return hourlyData;
        } else {
            throw new RuntimeException("Failed to fetch hourly weather data for " + city);
        }
    }
}
