package pl.airq.common.infrastructure.services.notifier;

import io.smallrye.mutiny.Uni;
import java.util.List;
import pl.airq.common.domain.notification.FailureNotificationDto;
import pl.airq.common.domain.notification.NotificationGroup;
import pl.airq.common.process.failure.Failure;

public class NotifierService {

    private final FailureNotificationFactory failureNotificationFactory;
    private final NotifierRestClient notifierClient;

    public NotifierService(FailureNotificationFactory failureNotificationFactory,
                           NotifierRestClient notifierClient) {
        this.failureNotificationFactory = failureNotificationFactory;
        this.notifierClient = notifierClient;
    }

    public Uni<Void> failure(Failure failure, List<NotificationGroup> group) {
        FailureNotificationDto dto = failureNotificationFactory.from(failure, group);
        return notifierClient.failureNotification(dto);
    }

}
