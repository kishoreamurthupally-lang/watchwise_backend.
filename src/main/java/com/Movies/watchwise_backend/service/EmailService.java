package com.Movies.watchwise_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(" WatchWise OTP Verification");
            message.setText("Your OTP for WatchWise registration is: " + otp + 
                           "\n\nThis OTP is valid for 5 minutes.\nDo not share it with anyone.\n\nRegards,\nWatchWise Team");

            mailSender.send(message);
            System.out.println(" OTP Email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.err.println(" Failed to send OTP email to " + toEmail);
            e.printStackTrace();   // Console lo full error chudataniki
            throw new RuntimeException("Failed to send OTP. Please try again later.");
        }
    }

    public void sendWelcomeEmail(String toEmail, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(" Welcome to WatchWise!");
            message.setText("Hi " + username + ",\n\nWelcome to WatchWise! Enjoy exploring movies \n\nRegards,\nWatchWise Team");

            mailSender.send(message);
            System.out.println(" Welcome Email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println(" Failed to send Welcome email");
            e.printStackTrace();
        }
    }
}