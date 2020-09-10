package pl.airq.common.domain.process.command;

import io.smallrye.mutiny.Uni;
import pl.airq.common.domain.process.Try;

public interface Responder<C extends AppCommand<?, R>, R> {

    Uni<Try> respond(C command);

    default Uni<Try> castResponse(Uni<R> response) {
        return Try.raw(response);
    }

    default Uni<Try> castResponse(R response) {
        return castResponse(Uni.createFrom().item(response));
    }
}
