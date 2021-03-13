package pl.airq.common.process.failure;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DefaultFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFailureHandler.class);

    @ConsumeEvent(Failure.DEFAULT_FAILURE_TOPIC)
    public void handle(Failure event) {
        LOGGER.info("Failure occurred: {}", event);
    }
}
