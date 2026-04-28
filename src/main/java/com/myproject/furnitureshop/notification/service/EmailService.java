package com.myproject.furnitureshop.notification.service;

import com.myproject.furnitureshop.notification.model.NotificationMessage;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleEmail(NotificationMessage emailChannel) throws MessagingException;
}
