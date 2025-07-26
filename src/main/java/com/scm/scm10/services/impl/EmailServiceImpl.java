package com.scm.scm10.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.scm.scm10.services.EmailService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender eMailSender;

    // @Value("${spring.mail.username}")
    // private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        // message.setFrom(fromEmail);
        // message.setFrom("scm@demomailtrap.com");
        // message.setFrom("apismtp@mailtrap.io");
        // message.setFrom("sarimbinasif12345@gmail.com");
        message.setFrom("from@example.com");
        eMailSender.send(message);
        // eMailSender.send("youremail@example.com", "Test Subject", "Hello from Spring Boot via Mailtrap!");

    }

    @Override
    public void sendEmailWithHtml() {
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
        // emailService.sendEmail("youremail@example.com", "Test Subject", "Hello from Spring Boot via Mailtrap!");


    }

    @Override
    public void sendEmailWithAttachment() {
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
    }
}
