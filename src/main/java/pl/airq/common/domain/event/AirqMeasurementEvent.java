package pl.airq.common.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.process.event.AirqEvent;

@RegisterForReflection
@JsonIgnoreProperties(value = {"eventType"})
public class AirqMeasurementEvent extends AirqEvent<AirqMeasurementPayload> {

    public AirqMeasurementEvent(OffsetDateTime timestamp, AirqMeasurementPayload payload) {
        super(timestamp, payload, AirqMeasurementEvent.class);
    }

    @Override
    public String toString() {
        return "AirqMeasurement{" +
                "eventType='" + eventType + '\'' +
                ", timestamp=" + timestamp.toString() +
                ", payload=" + payload.toString() +
                '}';
    }
}
