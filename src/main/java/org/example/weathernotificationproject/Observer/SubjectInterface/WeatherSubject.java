package org.example.weathernotificationproject.Observer.SubjectInterface;

import org.example.weathernotificationproject.Observer.ObserverInterface.WeatherObserver;
import org.example.weathernotificationproject.WeatherData;

public interface WeatherSubject {
    void addObserver(WeatherObserver observer);
    void removeObserver(WeatherObserver observer);
    void notifyObservers(WeatherData weatherData);
}
