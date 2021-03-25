package pl.airq.common.process.ctx.gios.aggragation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class GiosMeasurementDeletedEvent extends AirqEvent<GiosMeasurementEventPayload> {

    @JsonCreator
    public GiosMeasurementDeletedEvent(@JsonProperty("timestamp") Timestamp timestamp,
                                       @JsonProperty("payload") GiosMeasurementEventPayload payload) {
        super(timestamp, payload, GiosMeasurementDeletedEvent.class);
    }

    @Override
    public String toString() {
        return "GiosMeasurementDeletedEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
