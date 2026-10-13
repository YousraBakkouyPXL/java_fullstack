package be.pxl.student.notificationservice.service;

import be.pxl.student.notificationservice.domain.dto.NotificationRequest;
import be.pxl.student.notificationservice.domain.dto.NotificationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationSender notificationSender;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void sendNotification_sendsRequestMessage() {
        NotificationRequest request = new NotificationRequest();
        request.setMessage("Hello");

        NotificationResponse response = notificationService.sendNotification(request);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(notificationSender).send(captor.capture());

        assertEquals("Hello", captor.getValue());
        assertEquals("sent", response.getStatus());
    }
}
