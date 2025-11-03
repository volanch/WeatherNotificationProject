package org.example.weathernotificationproject.Strategy.StrategyInterface;

import org.example.weathernotificationproject.WeatherData;

import java.util.List;

public interface UpdateStrategyWithLists {
    List<WeatherData> updateHourly(String city);
}
