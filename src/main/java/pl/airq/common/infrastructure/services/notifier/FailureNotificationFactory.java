package pl.airq.common.infrastructure.services.notifier;

import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import pl.airq.common.domain.notification.FailureNotificationDto;
import pl.airq.common.domain.notification.NotificationGroup;
import pl.airq.common.process.failure.Failure;

class FailureNotificationFactory {

    private final String applicationName;

    FailureNotificationFactory(String applicationName) {
        this.applicationName = applicationName;
    }

    FailureNotificationDto from(Failure failure, List<NotificationGroup> groups) {
        FailureNotificationDto dto = new FailureNotificationDto();
        dto.applicationName = applicationName;
        dto.groups = groups;
        dto.timestamp = failure.timestamp;
        dto.errorMessage = failure.payload.throwable.getMessage();
        dto.stackTrace = ExceptionUtils.getStackTrace(failure.payload.throwable);

        return dto;
    }
}
