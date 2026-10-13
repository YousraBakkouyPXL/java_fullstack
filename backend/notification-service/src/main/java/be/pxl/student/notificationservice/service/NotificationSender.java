package be.pxl.student.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationSender.class);

    public void send(String message) {
        LOGGER.info("Notification sent: {}", message);
    }
}
