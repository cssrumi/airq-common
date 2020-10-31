package pl.airq.common.process.ctx.enriched;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class AirqDataEnrichedEvent extends AirqEvent<AirqDataEnrichedPayload> {

    @JsonCreator
    public AirqDataEnrichedEvent(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                 @JsonProperty("payload") AirqDataEnrichedPayload payload) {
        super(timestamp, payload, AirqDataEnrichedEvent.class);
    }

    @Override
    public String toString() {
        return "AirqDataEnrichedEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
