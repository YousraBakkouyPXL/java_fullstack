package be.pxl.student.notificationservice.service;

import be.pxl.student.notificationservice.domain.dto.NotificationRequest;
import be.pxl.student.notificationservice.domain.dto.NotificationResponse;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationSender notificationSender;

    public NotificationService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public NotificationResponse sendNotification(NotificationRequest request) {
        notificationSender.send(request.getMessage());
        return new NotificationResponse(request.getMessage(), "sent");
    }
}
