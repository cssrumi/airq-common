package pl.airq.common.domain.process.event;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.process.Payload;

@RegisterForReflection
public abstract class AirqEvent<P extends Payload> implements Event {

    public final OffsetDateTime timestamp;
    public final P payload;
    public final String eventType;

    protected AirqEvent(OffsetDateTime timestamp, P payload, Class<?> eventType) {
        this.timestamp = timestamp;
        this.payload = payload;
        this.eventType = eventType.getSimpleName();
    }
}
