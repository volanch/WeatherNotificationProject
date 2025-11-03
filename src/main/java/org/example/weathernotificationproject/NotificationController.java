package org.example.weathernotificationproject;

import org.example.weathernotificationproject.Factory.Notification;
import org.example.weathernotificationproject.Factory.NotificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationFactory notificationFactory;

    @PostMapping
    public String sendNotification(
            @RequestParam String type,
            @RequestParam String recipient,
            @RequestParam String message
    ) {
        Notification notification = notificationFactory.createNotification(type);
        notification.sendNotification(message, recipient);
        return "Notification sent via " + type;
    }
}
