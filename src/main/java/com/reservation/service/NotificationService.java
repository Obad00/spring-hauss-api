package com.reservation.service;

import com.reservation.entity.Notification;
import com.reservation.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Créer une notification
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
