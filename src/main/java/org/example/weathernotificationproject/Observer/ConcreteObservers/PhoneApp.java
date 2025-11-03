package org.example.weathernotificationproject.Observer.ConcreteObservers;

import org.example.weathernotificationproject.Observer.ObserverInterface.WeatherObserver;
import org.example.weathernotificationproject.WeatherData;
import org.springframework.stereotype.Component;

@Component
public class PhoneApp implements WeatherObserver {

    @Override
    public void update(WeatherData weatherData) {
        System.out.println("PhoneApp: received new weather update: " + weatherData);
    }

}
