package pl.airq.common.domain.notification;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import java.util.List;

@RegisterForReflection
public class FailureNotificationDto {

    public OffsetDateTime timestamp;
    public String applicationName;
    public String errorMessage;
    public String stackTrace;
    public List<NotificationGroup> groups;

}
