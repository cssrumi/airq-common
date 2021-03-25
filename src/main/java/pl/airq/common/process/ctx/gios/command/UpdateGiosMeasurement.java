package pl.airq.common.process.ctx.gios.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.command.AirqCommand;

@RegisterForReflection
public class UpdateGiosMeasurement extends AirqCommand<UpdateGiosMeasurementPayload> {

    @JsonCreator
    public UpdateGiosMeasurement(@JsonProperty("timestamp") Timestamp timestamp,
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
