package be.pxl.student.notificationservice.domain.dto;

public class NotificationResponse {

    private String message;
    private String status;

    public NotificationResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
