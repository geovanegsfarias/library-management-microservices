package com.github.geovanegsfarias.service;

import com.github.geovanegsfarias.model.Notification;
import com.github.geovanegsfarias.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    public NotificationService(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void sendNotification(Notification notification) {
        var message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(notification.getTo());
        message.setSubject(notification.getSubject());
        message.setText(notification.getBody());
        mailSender.send(message);
        save(notification);
    }
}