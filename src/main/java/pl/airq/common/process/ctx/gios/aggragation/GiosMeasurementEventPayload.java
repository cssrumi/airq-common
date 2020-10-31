package pl.airq.common.process.ctx.gios.aggragation;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.gios.GiosMeasurement;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class GiosMeasurementEventPayload implements Payload {

    public final GiosMeasurement measurement;

    @JsonCreator
    public GiosMeasurementEventPayload(GiosMeasurement measurement) {
        this.measurement = measurement;
    }

    @Override
    public String toString() {
        return "GiosMeasurementPayload{" +
                "measurement=" + measurement +
                '}';
    }
}
