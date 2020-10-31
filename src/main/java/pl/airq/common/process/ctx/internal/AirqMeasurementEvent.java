package pl.airq.common.process.ctx.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class AirqMeasurementEvent extends AirqEvent<AirqMeasurementPayload> {

    @JsonCreator
    public AirqMeasurementEvent(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                @JsonProperty("payload") AirqMeasurementPayload payload) {
        super(timestamp, payload, AirqMeasurementEvent.class);
    }

    @Override
    public String toString() {
        return "AirqMeasurement{" +
                "timestamp=" + timestamp.toString() +
                ", payload=" + payload.toString() +
                '}';
    }
}
