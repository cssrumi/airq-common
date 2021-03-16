package pl.airq.common.process.failure;

import io.smallrye.mutiny.Uni;

public interface FailureHandler {

    Uni<Void> handle(Failure failure);

}
