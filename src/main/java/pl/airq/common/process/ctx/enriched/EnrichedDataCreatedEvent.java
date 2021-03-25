package pl.airq.common.process.ctx.enriched;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class EnrichedDataCreatedEvent extends AirqEvent<EnrichedDataEventPayload> {

    @JsonCreator
    public EnrichedDataCreatedEvent(@JsonProperty("timestamp") Timestamp timestamp,
                                    @JsonProperty("payload") EnrichedDataEventPayload payload) {
        super(timestamp, payload, EnrichedDataCreatedEvent.class);
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
