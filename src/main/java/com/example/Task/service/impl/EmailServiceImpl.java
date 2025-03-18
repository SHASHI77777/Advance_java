package com.example.Task.service.impl;


import com.example.Task.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String content) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shashiavaali@gmail.com");
        message.setTo(to);
        message.setText(content);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Sent Successfully....");

    }

}


