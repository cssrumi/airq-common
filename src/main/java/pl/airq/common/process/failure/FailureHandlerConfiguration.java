package pl.airq.common.process.failure;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import pl.airq.common.infrastructure.services.notifier.NotifierService;

@Startup
@Dependent
class FailureHandlerConfiguration {

    @IfBuildProperty(name = "failure.enable-logging", stringValue = "true", enableIfMissing = true)
    @ApplicationScoped
    FailureHandler loggingFailureHandler() {
        return new LoggingFailureHandler();
    }

    @IfBuildProperty(name = "failure.enable-notifications", stringValue = "true")
    @ApplicationScoped
    FailureHandler notificationFailureHandler(NotifierService notifierService) {
        return new NotifierFailureHandler(notifierService);
    }

}
