package pl.airq.common.process.ctx.gios.installation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class GiosInstallationCreatedEvent extends AirqEvent<GiosInstallationEventPayload> {

    @JsonCreator
    public GiosInstallationCreatedEvent(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                        @JsonProperty("payload") GiosInstallationEventPayload payload) {
        super(timestamp, payload, GiosInstallationCreatedEvent.class);
    }

    @Override
    public String toString() {
        return "GiosInstallationCreatedEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
