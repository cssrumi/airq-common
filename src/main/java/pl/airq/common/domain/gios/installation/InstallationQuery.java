package pl.airq.common.domain.gios.installation;

import io.smallrye.mutiny.Uni;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Set;
import pl.airq.common.store.key.TSFKey;

public interface InstallationQuery {

    Uni<Set<Installation>> getAll();

    Uni<Set<Installation>> getAllWithPMSinceLastHour();

    Uni<Set<Installation>> getAllWithPMSinceLastNHours(int n);

    Uni<Installation> getByTSFKey(TSFKey key);

}
