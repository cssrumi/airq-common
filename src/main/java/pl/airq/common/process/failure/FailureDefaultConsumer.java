package pl.airq.common.process.failure;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.process.event.Consumer;

@ApplicationScoped
public class FailureDefaultConsumer implements Consumer<Failure> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureDefaultConsumer.class);

    @Inject
    public FailureDefaultConsumer() {
    }

    @ConsumeEvent(Failure.DEFAULT_FAILURE_TOPIC)
    Uni<Void> consumeEvent(Failure event) {
        return consume(event);
    }

    @Override
    public Uni<Void> consume(Failure event) {
        return Uni.createFrom().voidItem()
                  .onItem().invoke(ignore -> LOGGER.info("Failure occurred: {}", event));
    }
}
