package pl.airq.common.process.event;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.Payload;

@RegisterForReflection
public abstract class AppEvent<P extends Payload> implements Event {

    public final Timestamp timestamp;
    public final P payload;

    protected AppEvent(Timestamp timestamp, P payload) {
        this.timestamp = timestamp;
        this.payload = payload;
    }

    protected AppEvent(P payload) {
        this(Timestamp.now(), payload);
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
