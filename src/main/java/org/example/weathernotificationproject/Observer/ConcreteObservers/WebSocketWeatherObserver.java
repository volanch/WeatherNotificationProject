package org.example.weathernotificationproject.Observer.ConcreteObservers;

import org.example.weathernotificationproject.Observer.ObserverInterface.WeatherObserver;
import org.example.weathernotificationproject.WeatherData;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class WebSocketWeatherObserver implements WeatherObserver {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketWeatherObserver(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void update(WeatherData data){
        messagingTemplate.convertAndSend("/topic/weather", data);
    }

}
