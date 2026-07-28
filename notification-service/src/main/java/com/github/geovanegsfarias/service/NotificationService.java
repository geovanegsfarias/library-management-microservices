package com.github.geovanegsfarias.service;

import com.github.geovanegsfarias.dto.LoanOverdueEvent;
import com.github.geovanegsfarias.model.Notification;
import com.github.geovanegsfarias.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
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

    public void sendNotification(Notification notification, LoanOverdueEvent event) {
        notification.setSubject(buildSubject(event));
        notification.setBody(buildBody(event));

        var message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(notification.getRecipient());
        message.setSubject(notification.getSubject());
        message.setText(notification.getBody());

        try {
            mailSender.send(message);
            log.debug("Overdue notification email sent to {}", notification.getRecipient());
        } catch (MailException e) {
            log.error("Error sending email", e);
        }

        save(notification);
    }

    private String buildSubject(LoanOverdueEvent event) {
        return "Your loan is overdue: " + event.bookTitle();
    }

    private String buildBody(LoanOverdueEvent event) {
        var formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy").withZone(ZoneOffset.UTC);

        return "The book \"%s\" was due on %s and has not been returned yet."
                .formatted(event.bookTitle(), formatter.format(event.dueDate()));
    }

}