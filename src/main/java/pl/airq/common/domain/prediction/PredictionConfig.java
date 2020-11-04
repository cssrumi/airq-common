package pl.airq.common.domain.prediction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.StringJoiner;

@RegisterForReflection
public class PredictionConfig {

    public final Long timeframe;
    public final ChronoUnit timeUnit;
    public final String field;

    @JsonCreator
    public PredictionConfig(@JsonProperty("timeframe") Long timeframe,
                            @JsonProperty("timeUnit") ChronoUnit timeUnit,
                            @JsonProperty("field") String field) {
        this.timeframe = timeframe;
        this.timeUnit = timeUnit;
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PredictionConfig that = (PredictionConfig) o;
        return Objects.equals(timeframe, that.timeframe) &&
                timeUnit == that.timeUnit &&
                Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeframe, timeUnit, field);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PredictionConfig.class.getSimpleName() + "[", "]")
                .add("timeframe=" + timeframe)
                .add("timeUnit=" + timeUnit)
                .add("field='" + field + "'")
                .toString();
    }
}
