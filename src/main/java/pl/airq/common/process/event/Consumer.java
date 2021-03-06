package pl.airq.common.process.event;

import io.smallrye.mutiny.Uni;

public interface Consumer<E extends Event> {

    Uni<Void> consume(E event);
}
