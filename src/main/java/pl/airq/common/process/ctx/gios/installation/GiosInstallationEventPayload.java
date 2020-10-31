package pl.airq.common.process.ctx.gios.installation;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.gios.Installation;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class GiosInstallationEventPayload implements Payload {

    public final Installation installation;

    @JsonCreator
    public GiosInstallationEventPayload(Installation installation) {
        this.installation = installation;
    }

    @Override
    public String toString() {
        return "GiosInstallationPayload{" +
                "installation=" + installation +
                '}';
    }
}
