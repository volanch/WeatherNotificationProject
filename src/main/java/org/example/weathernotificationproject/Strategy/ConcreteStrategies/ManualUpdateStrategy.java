package org.example.weathernotificationproject.Strategy.ConcreteStrategies;

import org.example.weathernotificationproject.Strategy.StrategyInterface.UpdateStrategy;
import org.example.weathernotificationproject.WeatherData;
import org.springframework.stereotype.Component;

@Component
public class ManualUpdateStrategy implements UpdateStrategy {
    private WeatherData manualData;

    public void setManualData(WeatherData manualData) {
        this.manualData = manualData;
    }

    @Override
    public WeatherData update(String city) {
        if (manualData == null){
            System.out.println("manualData is null");
            return null;
        }else{
            return new WeatherData.Builder()
                    .city(city)
                    .temperature(manualData.getTemperature())
                    .apparentTemperature(manualData.getApparentTemperature())
                    .humidity(manualData.getHumidity())
                    .windSpeed(manualData.getWindSpeed())
                    .precipitation(manualData.getPrecipitation())
                    .rain(manualData.getRain())
                    .weatherCode(manualData.getWeatherCode())
                    .build();
        }

    }
}

