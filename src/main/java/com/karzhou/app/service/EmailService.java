package com.karzhou.app.service;

import com.karzhou.app.config.Config;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    private final String fromEmail = Config.getProperty("mail.username");
    private final String password = Config.getProperty("mail.password");

    public void sendConfirmationEmail(String toEmail, String token) {
        String subject = "Email Confirmation";
        String confirmationLink = "http://localhost:8080/confirm?token=" + token;
        String content = "Thank you for registering!\n\nClick the link below to confirm your email:\n" + confirmationLink;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("Confirmation email sent to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
