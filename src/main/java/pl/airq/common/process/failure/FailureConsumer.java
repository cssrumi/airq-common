package pl.airq.common.process.failure;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
class FailureConsumer {

    private final List<FailureHandler> handlers;

    @Inject
    FailureConsumer(Instance<FailureHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toUnmodifiableList());
    }

    @ConsumeEvent(Failure.DEFAULT_FAILURE_TOPIC)
    Uni<Void> consume(Failure failure) {
        return Multi.createFrom().iterable(handlers)
                    .call(handler -> handler.handle(failure))
                    .onOverflow().buffer(5)
                    .collect().asList()
                    .onItem().ignore().andContinueWithNull();
    }
}
