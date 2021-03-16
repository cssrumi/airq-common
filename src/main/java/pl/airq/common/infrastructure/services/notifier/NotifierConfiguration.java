package pl.airq.common.infrastructure.services.notifier;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import io.vertx.mutiny.core.Vertx;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.airq.common.config.properties.NotificationProperties;

@Startup
@IfBuildProperty(name = "notification.enabled", stringValue = "true", enableIfMissing = true)
@Dependent
class NotifierConfiguration {

    @ApplicationScoped
    FailureNotificationFactory failureNotificationFactory(@ConfigProperty(name = "quarkus.application.name") String applicationName) {
        return new FailureNotificationFactory(applicationName);
    }

    @ApplicationScoped
    NotifierRestClient notifierRestClient(NotificationProperties properties, Vertx vertx) {
        return new NotifierRestClient(properties, vertx);
    }

    @ApplicationScoped
    NotifierService notifierService(FailureNotificationFactory failureNotificationFactory, NotifierRestClient restClient) {
        return new NotifierService(failureNotificationFactory, restClient);
    }

}
