package pl.airq.common.domain.enriched;

import io.smallrye.mutiny.Uni;
import java.time.OffsetDateTime;
import java.util.Set;
import pl.airq.common.domain.vo.StationId;

public interface EnrichedDataQuery {

    Uni<Set<EnrichedData>> findAll();

    Uni<Set<EnrichedData>> findAllByStation(String name);

    Uni<EnrichedData> findLatestByStation(String name);

    Uni<Set<EnrichedData>> findAllByStationId(StationId stationId);

    Uni<Set<EnrichedData>> findAllByCoords(Float lon, Float lat);

    Uni<EnrichedData> findByStationAndTimestamp(String name, OffsetDateTime timestamp);

}
