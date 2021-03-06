package pl.airq.common.process.ctx.phenotype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public class AirqPhenotypeCreatedEvent extends AirqEvent<AirqPhenotypeCreatedPayload> {

    @JsonCreator
    public AirqPhenotypeCreatedEvent(@JsonProperty("timestamp") Timestamp timestamp,
                                     @JsonProperty("payload") AirqPhenotypeCreatedPayload payload) {
        super(timestamp, payload, AirqPhenotypeCreatedEvent.class);
    }

    @Override
    public String toString() {
        return "AirqPhenotypeCreatedEvent{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }
}
