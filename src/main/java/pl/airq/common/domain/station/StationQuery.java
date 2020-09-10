package pl.airq.common.domain.station;

import io.smallrye.mutiny.Uni;
import java.util.Set;
import pl.airq.common.vo.StationId;

public interface StationQuery {

    Uni<Set<Station>> findAll();
    Uni<Station> findById(StationId id);
    Uni<Set<Station>> findByName(String name);
}
