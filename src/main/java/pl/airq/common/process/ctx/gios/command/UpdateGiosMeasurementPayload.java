package pl.airq.common.process.ctx.gios.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.gios.UpdateSource;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class UpdateGiosMeasurementPayload implements Payload {

    public final OffsetDateTime timestamp;
    public final String station;
    public final Float oldPm10;
    public final Float newPm10;
    public final UpdateSource source;

    @JsonCreator
    public UpdateGiosMeasurementPayload(@JsonProperty("timestamp") OffsetDateTime timestamp,
                                        @JsonProperty("station") String station,
                                        @JsonProperty("oldPm10") Float oldPm10,
                                        @JsonProperty("newPm10") Float newPm10,
                                        @JsonProperty("source") UpdateSource source) {
        this.timestamp = timestamp;
        this.station = station;
        this.oldPm10 = oldPm10;
        this.newPm10 = newPm10;
        this.source = source;
    }

}
