package pl.airq.common.process.ctx.gios.aggragation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class GiosMeasurementCreatedEvent extends AirqEvent<GiosMeasurementEventPayload> {

    @JsonCreator
    public GiosMeasurementCreatedEvent(@JsonProperty("timestamp") Timestamp timestamp,
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
