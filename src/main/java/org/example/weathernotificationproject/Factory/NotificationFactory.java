package org.example.weathernotificationproject.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {
    @Autowired
    private OutlookNotification outlookNotification;

    @Autowired
    private GmailNotification gmailNotification;

    public Notification createNotification(String type) {
        if ("gmail".equalsIgnoreCase(type)) {
            return gmailNotification;
        } else {
            return outlookNotification;
        }
    }
}
