package org.example.weathernotificationproject.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("gmail")
public class GmailNotification implements Notification {

    @Autowired
    @Qualifier("gmailMailSender")
    private JavaMailSender javaMailSender;

    @Override
    public void sendNotification(String message, String recipient) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("vlad.kovernikov06@gmail.com");
        email.setTo(recipient);
        email.setSubject("Weather Notification");
        email.setText(message);

        javaMailSender.send(email);
    }
}
