package org.example.weathernotificationproject.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.gmail.host}")
    private String gmailHost;

    @Value("${spring.mail.gmail.port}")
    private int gmailPort;

    @Value("${spring.mail.gmail.username}")
    private String gmailUsername;

    @Value("${spring.mail.gmail.password}")
    private String gmailPassword;

    @Value("${spring.mail.outlook.host}")
    private String outlookHost;

    @Value("${spring.mail.outlook.port}")
    private int outlookPort;

    @Value("${spring.mail.outlook.username}")
    private String outlookUsername;

    @Value("${spring.mail.outlook.password}")
    private String outlookPassword;

    @Bean("gmailMailSender")
    public JavaMailSender gmailMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(gmailHost);
        mailSender.setPort(gmailPort);
        mailSender.setUsername(gmailUsername);
        mailSender.setPassword(gmailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }

    @Bean("outlookMailSender")
    public JavaMailSender outlookMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(outlookHost);
        mailSender.setPort(outlookPort);
        mailSender.setUsername(outlookUsername);
        mailSender.setPassword(outlookPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
