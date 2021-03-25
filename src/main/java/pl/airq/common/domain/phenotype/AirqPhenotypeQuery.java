package pl.airq.common.domain.phenotype;

import io.smallrye.mutiny.Uni;
import java.util.Set;
import pl.airq.common.domain.vo.StationId;

public interface AirqPhenotypeQuery {

    Uni<Set<AirqPhenotype>> findAll();

    Uni<Set<AirqPhenotype>> findByStationId(StationId stationId);

    Uni<AirqPhenotype> findLatestByStationId(StationId stationId);

    Uni<AirqPhenotype> findBestByStationId(StationId stationId);
}
