package pl.airq.common.process.failure;

import io.smallrye.mutiny.Uni;
import java.util.List;
import pl.airq.common.config.properties.FailureProperties;
import pl.airq.common.domain.notification.NotificationGroup;
import pl.airq.common.infrastructure.services.notifier.NotifierService;

class NotifierFailureHandler implements FailureHandler {

    private final NotifierService notifierService;
    private final List<NotificationGroup> deliverTo;

    NotifierFailureHandler(NotifierService notifierService, List<NotificationGroup> deliverTo) {
        this.notifierService = notifierService;
        this.deliverTo = deliverTo;
    }

    public Uni<Void> handle(Failure failure) {
        return notifierService.failure(failure, deliverTo);
    }

}
