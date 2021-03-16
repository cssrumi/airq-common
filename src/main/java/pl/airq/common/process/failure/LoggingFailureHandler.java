package pl.airq.common.process.failure;

import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoggingFailureHandler implements FailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFailureHandler.class);

    @Override
    public Uni<Void> handle(Failure event) {
        return Uni.createFrom().voidItem()
                  .invoke(() -> LOGGER.info("Failure occurred: {}", event));
    }

}
