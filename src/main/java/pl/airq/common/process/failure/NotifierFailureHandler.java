package pl.airq.common.process.failure;

import io.smallrye.mutiny.Uni;
import java.util.List;
import pl.airq.common.domain.notification.NotificationGroup;
import pl.airq.common.infrastructure.services.notifier.NotifierService;

class NotifierFailureHandler implements FailureHandler {

    private final NotifierService notifierService;

    NotifierFailureHandler(NotifierService notifierService) {
        this.notifierService = notifierService;
    }

    public Uni<Void> handle(Failure failure) {
        return notifierService.failure(failure, List.of(NotificationGroup.DEV, NotificationGroup.PO));
    }

}
