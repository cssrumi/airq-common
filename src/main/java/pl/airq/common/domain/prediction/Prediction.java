package pl.airq.common.domain.prediction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import pl.airq.common.util.TimestampUtil;
import pl.airq.common.domain.vo.StationId;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prediction that = (Prediction) o;
        return TimestampUtil.isEqual(timestamp, that.timestamp) &&
                Objects.equals(value, that.value) &&
                Objects.equals(config, that.config) &&
                Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, value, config, stationId);
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
