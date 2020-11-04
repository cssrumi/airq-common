package pl.airq.common.process.ctx.enriched;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class EnrichedDataDeletedEvent extends AirqEvent<EnrichedDataEventPayload> {

    @JsonCreator
    public EnrichedDataDeletedEvent(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                    @JsonProperty("payload") EnrichedDataEventPayload payload) {
        super(timestamp, payload, EnrichedDataDeletedEvent.class);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("timestamp", timestamp)
                .append("payload", payload)
                .append("eventType", eventType)
                .toString();
    }
}
