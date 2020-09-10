package pl.airq.common.domain.process.event;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.process.Payload;

@RegisterForReflection
public abstract class AppEvent<P extends Payload> implements Event {

    public final OffsetDateTime timestamp;
    public final P payload;

    protected AppEvent(OffsetDateTime timestamp, P payload) {
        this.timestamp = timestamp;
        this.payload = payload;
    }

    protected AppEvent(P payload) {
        this(OffsetDateTime.now(), payload);
    }

    public abstract String defaultTopic();

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
