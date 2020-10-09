package pl.airq.common.domain.gios;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.gios.installation.Installation;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class GiosMeasurementEventPayload implements Payload {

    public final Installation installation;

    @JsonCreator
    public GiosMeasurementEventPayload(Installation installation) {
        this.installation = installation;
    }

    @Override
    public String toString() {
        return "GiosMeasurementPayload{" +
                "installation=" + installation +
                '}';
    }
}
