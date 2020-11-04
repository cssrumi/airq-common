package pl.airq.common.process.ctx.enriched;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.airq.common.domain.enriched.EnrichedData;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class EnrichedDataEventPayload implements Payload {

    public final EnrichedData enrichedData;

    @JsonCreator
    public EnrichedDataEventPayload(EnrichedData enrichedData) {
        this.enrichedData = enrichedData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("enrichedData", enrichedData)
                .toString();
    }
}
