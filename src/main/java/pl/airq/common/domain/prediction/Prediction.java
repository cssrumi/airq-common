package pl.airq.common.domain.prediction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import java.util.StringJoiner;
import pl.airq.common.vo.StationId;

@RegisterForReflection
public class Prediction {

    public final OffsetDateTime timestamp;
    public final Double value;
    public final PredictionConfig config;
    public final StationId stationId;

    @JsonCreator
    public Prediction(@JsonProperty("timestamp") OffsetDateTime timestamp,
                      @JsonProperty("value") Double value,
                      @JsonProperty("config") PredictionConfig config,
                      @JsonProperty("stationId") StationId stationId) {
        this.timestamp = timestamp;
        this.value = value;
        this.config = config;
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Prediction.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("value=" + value)
                .add("config=" + config)
                .add("stationId=" + stationId)
                .toString();
    }
}
