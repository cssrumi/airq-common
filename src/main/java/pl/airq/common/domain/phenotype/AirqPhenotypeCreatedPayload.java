package pl.airq.common.domain.phenotype;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class AirqPhenotypeCreatedPayload implements Payload {

    public final AirqPhenotype airqPhenotype;

    @JsonCreator
    public AirqPhenotypeCreatedPayload(AirqPhenotype airqPhenotype) {
        this.airqPhenotype = airqPhenotype;
    }

    @Override
    public String toString() {
        return "AirqPhenotypeCreatedPayload{" +
                "airqPhenotype=" + airqPhenotype +
                '}';
    }
}
