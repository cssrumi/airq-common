package pl.airq.common.domain.prediction;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.vo.StationId;

@RegisterForReflection
public class Prediction {

    public final OffsetDateTime timestamp;
    public final Double value;
    public final PredictionConfig config;
    public final StationId stationId;

    public Prediction(OffsetDateTime timestamp, Double value, PredictionConfig config, StationId stationId) {
        this.timestamp = timestamp;
        this.value = value;
        this.config = config;
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "timestamp=" + timestamp +
                ", value=" + value +
                ", config=" + config +
                ", stationId=" + stationId +
                '}';
    }
}
