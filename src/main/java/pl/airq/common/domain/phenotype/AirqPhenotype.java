package pl.airq.common.domain.phenotype;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import pl.airq.common.domain.prediction.PredictionConfig;
import pl.airq.common.util.TimestampUtil;
import pl.airq.common.domain.vo.StationId;

@RegisterForReflection
public class AirqPhenotype {

    public final OffsetDateTime timestamp;
    public final StationId stationId;
    public final List<String> fields;
    public final List<Float> values;
    public final PredictionConfig prediction;
    public final Double fitness;

    public AirqPhenotype(OffsetDateTime timestamp, StationId stationId, List<String> fields, List<Float> values,
                         PredictionConfig prediction, Double fitness) {
        this.timestamp = timestamp;
        this.stationId = stationId;
        this.fields = fields;
        this.values = values;
        this.prediction = prediction;
        this.fitness = fitness;
    }

    public Map<String, Float> fieldValueMap() {
        Map<String, Float> map = new HashMap<>();
        for (int i = 0; i < fields.size(); i++) {
            map.put(fields.get(i), values.get(i));
        }

        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AirqPhenotype phenotype = (AirqPhenotype) o;
        return TimestampUtil.isEqual(timestamp, phenotype.timestamp) &&
                Objects.equals(stationId, phenotype.stationId) &&
                Objects.equals(fields, phenotype.fields) &&
                Objects.equals(values, phenotype.values) &&
                Objects.equals(prediction, phenotype.prediction) &&
                Objects.equals(fitness, phenotype.fitness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, stationId, fields, values, prediction, fitness);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AirqPhenotype.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("stationId=" + stationId)
                .add("map=" + fieldValueMap())
                .add("fitness=" + fitness)
                .toString();
    }
}
