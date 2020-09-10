package pl.airq.common.domain.phenotype;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.airq.common.vo.StationId;

@RegisterForReflection
public class AirqPhenotype {

    public final OffsetDateTime timestamp;
    public final StationId stationId;
    public final List<String> fields;
    public final List<Float> values;
    public final Prediction prediction;
    public final Double fitness;

    public AirqPhenotype(OffsetDateTime timestamp, StationId stationId, List<String> fields, List<Float> values,
                         Prediction prediction, Double fitness) {
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
    public String toString() {
        return "AirqPhenotype{" +
                "map=" + fieldValueMap() +
                ", fitness=" + fitness +
                '}';
    }
}
