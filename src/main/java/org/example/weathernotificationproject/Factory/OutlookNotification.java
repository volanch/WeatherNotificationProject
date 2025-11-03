package org.example.weathernotificationproject.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("outlook")
public class OutlookNotification implements Notification {

    @Autowired
    @Qualifier("outlookMailSender")
    private JavaMailSender javaMailSender;

    @Override
    public void sendNotification(String message, String recipient) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("240454@astanit.edu.kz");
        email.setTo(recipient);
        email.setSubject("Weather Notification");
        email.setText(message);

        javaMailSender.send(email);
    }
}
