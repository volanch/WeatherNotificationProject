package org.example.weathernotificationproject.Services;

import org.example.weathernotificationproject.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WeatherWebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendWeatherUpdate(WeatherData weatherData) {
        messagingTemplate.convertAndSend("/topic/weather", weatherData);
    }
}