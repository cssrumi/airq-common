package pl.airq.common.domain.gios;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class GiosMeasurementCreatedEvent extends AirqEvent<GiosMeasurementEventPayload> {

    @JsonCreator
    public GiosMeasurementCreatedEvent(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                       @JsonProperty("payload") GiosMeasurementEventPayload payload) {
        super(timestamp, payload, GiosMeasurementCreatedEvent.class);
    }

    @Override
    public String toString() {
        return "GiosMeasurementCreatedEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
