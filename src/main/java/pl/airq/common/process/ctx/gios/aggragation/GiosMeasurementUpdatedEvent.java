package pl.airq.common.process.ctx.gios.aggragation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class GiosMeasurementUpdatedEvent extends AirqEvent<GiosMeasurementEventPayload> {

    @JsonCreator
    public GiosMeasurementUpdatedEvent(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                       @JsonProperty("payload") GiosMeasurementEventPayload payload) {
        super(timestamp, payload, GiosMeasurementUpdatedEvent.class);
    }

    @Override
    public String toString() {
        return "GiosMeasurementUpdatedEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
