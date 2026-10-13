package be.pxl.student.notificationservice.controller;

import be.pxl.student.notificationservice.domain.dto.NotificationRequest;
import be.pxl.student.notificationservice.domain.dto.NotificationResponse;
import be.pxl.student.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse sendNotification(@Valid @RequestBody NotificationRequest request) {
        return notificationService.sendNotification(request);
    }
}
