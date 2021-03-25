package pl.airq.common.process.ctx.gios.installation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class GiosInstallationDeletedEvent extends AirqEvent<GiosInstallationEventPayload> {

    @JsonCreator
    public GiosInstallationDeletedEvent(@JsonProperty("timestamp") Timestamp timestamp,
                                        @JsonProperty("payload") GiosInstallationEventPayload payload) {
        super(timestamp, payload, GiosInstallationDeletedEvent.class);
    }

    @Override
    public String toString() {
        return "GiosInstallationDeletedEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
