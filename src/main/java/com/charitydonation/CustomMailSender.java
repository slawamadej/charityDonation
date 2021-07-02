package com.charitydonation;

import com.charitydonation.entity.Contact;
import com.charitydonation.entity.PasswordResetToken;
import com.charitydonation.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class CustomMailSender {

    private JavaMailSender emailSender;

    public CustomMailSender(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    public void sendConfirmationEmail(String email, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Confirm registration");
        message.setText("In order to activate your account click link: http://localhost:8080/confirm-email?token=" + token);
        emailSender.send(message);
    }

    public void sendContactEmail(Contact contact){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("slawamadej@gmail.com");
        message.setCc(contact.getEmail());
        message.setSubject("Contact form donation page");
        message.setText(contact.getMessage());
        emailSender.send(message);
    }

    public void sendChangePasswordLink(PasswordResetToken passwordResetToken){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(passwordResetToken.getUser().getUsername());
        message.setSubject("Change password");
        message.setText("In order to change your password click link: http://localhost:8080/changePassword?token=" + passwordResetToken.getToken());
        emailSender.send(message);
    }
}
