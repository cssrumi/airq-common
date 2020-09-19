package pl.airq.common.domain.prediction;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.temporal.ChronoUnit;

@RegisterForReflection
public class PredictionConfig {

    public final Long timeframe;
    public final ChronoUnit timeUnit;
    public final String field;

    public PredictionConfig(Long timeframe, ChronoUnit timeUnit, String field) {
        this.timeframe = timeframe;
        this.timeUnit = timeUnit;
        this.field = field;
    }
}
