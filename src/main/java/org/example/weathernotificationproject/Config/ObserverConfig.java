package org.example.weathernotificationproject.Config;

import jakarta.annotation.PostConstruct;
import org.example.weathernotificationproject.Observer.ConcreteObservers.WebSocketWeatherObserver;
import org.example.weathernotificationproject.Observer.ConcreteSubject.WeatherStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class ObserverConfig {

    @Autowired
    private WeatherStation weatherStation;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @PostConstruct
    public void init() {
        weatherStation.addObserver(new WebSocketWeatherObserver(simpMessagingTemplate));
    }
}
