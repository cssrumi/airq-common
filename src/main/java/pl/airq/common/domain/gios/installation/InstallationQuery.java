package pl.airq.common.domain.gios.installation;

import io.smallrye.mutiny.Uni;
import java.util.Set;

public interface InstallationQuery {

    Uni<Set<Installation>> getAll();

    Uni<Set<Installation>> getAllWithPMSinceLastHour();

}
