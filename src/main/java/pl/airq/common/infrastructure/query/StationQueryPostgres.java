package pl.airq.common.infrastructure.query;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.station.Station;
import pl.airq.common.domain.station.StationQuery;
import pl.airq.common.infrastructure.persistance.SqlLike;
import pl.airq.common.domain.vo.StationId;

@ApplicationScoped
public class StationQueryPostgres implements StationQuery {

    private static final String FIND_ALL_QUERY = "SELECT DISTINCT station, lon, lat FROM enriched_data GROUP BY station, lon, lat";
    private static final String FIND_BY_NAME_QUERY = "SELECT DISTINCT station, lon, lat FROM enriched_data WHERE station like $1 GROUP BY station, lon, lat";
    private static final String FIND_BY_ID_QUERY = "SELECT DISTINCT station, lon, lat FROM enriched_data WHERE station = $1 GROUP BY station, lon, lat";
    private final PgPool client;

    @Inject
    StationQueryPostgres(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<Set<Station>> findAll() {
        return client.query(FIND_ALL_QUERY)
                     .execute()
                     .map(result -> DBParser.parseSet(result, Station::from));
    }

    @Override
    public Uni<Station> findById(StationId id) {
        return client.preparedQuery(FIND_BY_ID_QUERY)
                     .execute(Tuple.of(id.value()))
                     .map(result -> DBParser.parseOne(result, Station::from));
    }

    @Override
    public Uni<Set<Station>> findByName(String name) {
        return client.preparedQuery(FIND_BY_NAME_QUERY)
                     .execute(Tuple.of(SqlLike.inAnyPosition(name)))
                     .map(result -> DBParser.parseSet(result, Station::from));
    }
}
