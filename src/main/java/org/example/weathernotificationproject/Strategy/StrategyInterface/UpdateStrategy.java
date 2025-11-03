package org.example.weathernotificationproject.Strategy.StrategyInterface;

import org.example.weathernotificationproject.WeatherData;

public interface UpdateStrategy {
    WeatherData update(String city);
}
