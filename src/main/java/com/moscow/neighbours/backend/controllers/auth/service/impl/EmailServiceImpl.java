package com.moscow.neighbours.backend.controllers.auth.service.impl;

import com.moscow.neighbours.backend.controllers.auth.service.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${app.team-email}")
    private String techEmail;

    @Value("${app.team-email.password}")
    private String techEmailPassword;

    private final String host = "smtp.yandex.ru";

    private final String port = "465";

    private final Properties properties;

    public EmailServiceImpl() {
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", host);
        properties.put("mail.smtp.ssl.enable", "true");
    }

    @PostConstruct
    public void test() {
        System.out.println("tech team email " + techEmail);
        System.out.println("tech team password " + techEmailPassword);
    }

    @Override
    public boolean isActive() {
        return techEmail != null && techEmailPassword != null;
    }

    @Override
    @Async
    public void sendVerificationEmail(String email, String name, String code) throws MessagingException {
        if (isActive()) {
            Session session = Session.getInstance(this.properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(techEmail, techEmailPassword);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(techEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Account Verification");

            String msg = String.format(template, name, code);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        }
    }

    // TODO: - russian version
//    private static final String template = "<div><p>Hello, %s!</p>" +
//            "<p>Confirm your account registration with this verification code: " +
//            "<b>%s</b></p>" +
//            "<p>Sincerely yours,<br>" +
//            "Development Team</p></div>";
    private static final String template = "<div><p>Привет, %s!</p>" +
            "<p>Подтверди регистрацию в приложении этим кодом: " +
            "<b>%s</b></p>" +
            "<p>С наилучшими пожеланиями,<br>" +
            "Миша, Никита и Яна</p></div>";

}

