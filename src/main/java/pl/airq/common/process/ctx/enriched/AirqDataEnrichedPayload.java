package pl.airq.common.process.ctx.enriched;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.enriched.EnrichedData;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class AirqDataEnrichedPayload implements Payload {

    public final EnrichedData enrichedData;

    @JsonCreator
    public AirqDataEnrichedPayload(EnrichedData enrichedData) {
        this.enrichedData = enrichedData;
    }

    @Override
    public String toString() {
        return "AirqDataEnrichedPayload{" +
                "enrichedData=" + enrichedData +
                '}';
    }
}
