package org.example.weathernotificationproject;

import org.example.weathernotificationproject.Strategy.ConcreteStrategies.ManualUpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manual-weather")
@CrossOrigin(origins = "*")
public class ManualWeatherController {

    @Autowired
    private ManualUpdateStrategy manualUpdateStrategy;

    @PostMapping("/set")
    public String setWeather(@RequestBody WeatherData weatherData) {
        manualUpdateStrategy.setManualData(weatherData);
        return "Manual weather updated!";
    }

    @GetMapping("/get")
    public WeatherData getWeather(@RequestParam String city) {
        return manualUpdateStrategy.update(city);
    }
}
