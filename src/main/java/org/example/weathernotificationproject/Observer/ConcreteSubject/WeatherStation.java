package org.example.weathernotificationproject.Observer.ConcreteSubject;

import lombok.Setter;
import org.example.weathernotificationproject.Observer.ObserverInterface.WeatherObserver;
import org.example.weathernotificationproject.Observer.SubjectInterface.WeatherSubject;
import org.example.weathernotificationproject.Strategy.StrategyInterface.UpdateStrategy;
import org.example.weathernotificationproject.WeatherData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherStation implements WeatherSubject {
    private final List<WeatherObserver> observers = new ArrayList<>();
    @Setter
    private UpdateStrategy updateStrategy;

    @Override
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(WeatherData weatherData) {
        for (WeatherObserver observer : observers) {
            observer.update(weatherData);
        }
    }
    public void fetchAndNotify(String city) {
        if (updateStrategy == null) return;
        WeatherData data = updateStrategy.update(city);
        if (data != null) notifyObservers(data);
    }

    public void fetchAndNotifyHourly(List<WeatherData> hourlyData) {
        if (hourlyData != null) {
            for (WeatherData data : hourlyData) {
                notifyObservers(data);
            }
        }
    }

    public void fetchAndNotifyWithRandom(WeatherData data) {
        if (data != null) {
            notifyObservers(data);
        }
    }
}
