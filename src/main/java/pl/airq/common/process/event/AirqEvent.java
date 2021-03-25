package pl.airq.common.process.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.Payload;

@JsonIgnoreProperties(value={ "eventType" }, allowGetters=true)
@RegisterForReflection
public abstract class AirqEvent<P extends Payload> implements Event {

    public final Timestamp timestamp;
    public final P payload;
    protected final String eventType;

    protected AirqEvent(Timestamp timestamp, P payload, Class<?> eventType) {
        this.timestamp = timestamp;
        this.payload = payload;
        this.eventType = eventType.getSimpleName();
    }

    @JsonProperty("eventType")
    public String eventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return "AirqEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
