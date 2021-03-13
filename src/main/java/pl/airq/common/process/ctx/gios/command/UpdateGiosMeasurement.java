package pl.airq.common.process.ctx.gios.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class UpdateGiosMeasurement extends AirqEvent<UpdateGiosMeasurementPayload> {

    @JsonCreator
    public UpdateGiosMeasurement(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                 @JsonProperty("payload") UpdateGiosMeasurementPayload payload) {
        super(timestamp, payload, UpdateGiosMeasurement.class);
    }

    @Override
    public String toString() {
        return "UpdateGiosMeasurement{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
