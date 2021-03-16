package pl.airq.common.infrastructure.services.notifier;

import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.config.properties.NotificationProperties;
import pl.airq.common.config.properties.RestService;
import pl.airq.common.domain.notification.FailureNotificationDto;

class NotifierRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifierRestClient.class);

    private final WebClient client;

    NotifierRestClient(NotificationProperties properties, Vertx vertx) {
        RestService rest = properties.getRest();
        this.client = WebClient.create(vertx, new WebClientOptions().setDefaultHost(rest.getHost())
                                                                    .setDefaultPort(rest.getPort())
                                                                    .setSsl(rest.getSsl())
                                                                    .setTrustAll(true));
    }

    Uni<Void> failureNotification(FailureNotificationDto dto) {
        return client.post("/api/v1/notifier/failure")
                     .sendJson(dto)
                     .onFailure().retry().withBackOff(Duration.ofSeconds(5)).atMost(3)
                     .onFailure().invoke(e -> LOGGER.error("Unable to send notification", e))
                     .onItem().ignore().andContinueWithNull();
    }

}
