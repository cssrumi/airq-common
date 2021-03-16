package pl.airq.common.domain.notification;

import java.time.OffsetDateTime;
import java.util.List;

public class FailureNotificationDto {

    public OffsetDateTime timestamp;
    public String applicationName;
    public String errorMessage;
    public String stackTrace;
    public List<NotificationGroup> groups;

}
