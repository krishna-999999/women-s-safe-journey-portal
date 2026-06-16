package com.krishna.safejourney.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SafeJourney OTP Verification");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
    }
    public void sendAlertEmail(String toEmail, String message) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setSubject("🚨 Safe Journey Live Location");
        mail.setText(message);

        mailSender.send(mail);
    }
}