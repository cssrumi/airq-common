package pl.airq.common.domain.gios;

import io.smallrye.mutiny.Uni;
import java.util.Set;
import pl.airq.common.infrastructure.store.key.TSFKey;

public interface InstallationQuery {

    Uni<Set<Installation>> getAll();

    Uni<Set<Installation>> getAllWithPMSinceLastHour();

    Uni<Set<Installation>> getAllWithPMSinceLastNHours(int n);

    Uni<Installation> getByTSFKey(TSFKey key);

}
