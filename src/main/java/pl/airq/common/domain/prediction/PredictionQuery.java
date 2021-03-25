package pl.airq.common.domain.prediction;

import io.smallrye.mutiny.Uni;
import java.util.Set;
import pl.airq.common.domain.vo.StationId;

public interface PredictionQuery {

    Uni<Prediction> findLatest(StationId stationId);

    Uni<Set<Prediction>> findAll(StationId stationId);

    Uni<Prediction> findLatestWithPredictionConfig(StationId stationId, PredictionConfig config);

}
