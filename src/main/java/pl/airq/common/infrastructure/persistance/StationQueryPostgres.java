package pl.airq.common.infrastructure.persistance;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowIterator;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.station.Station;
import pl.airq.common.domain.station.StationQuery;
import pl.airq.common.vo.StationId;

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
                     .map(this::parse);
    }

    @Override
    public Uni<Station> findById(StationId id) {
        return client.preparedQuery(FIND_BY_ID_QUERY)
                     .execute(Tuple.of(id.getId()))
                     .map(this::parseOne);
    }

    @Override
    public Uni<Set<Station>> findByName(String name) {
        return client.preparedQuery(FIND_BY_NAME_QUERY)
                     .execute(Tuple.of(SqlLike.inAnyPosition(name)))
                     .map(this::parse);
    }

    private Set<Station> parse(RowSet<Row> rows) {
        Set<Station> stations = new HashSet<>(rows.size());
        for (Row row : rows) {
            stations.add(Station.from(row));
        }

        return stations;
    }

    private Station parseOne(RowSet<Row> rows) {
        final RowIterator<Row> iterator = rows.iterator();
        if (iterator.hasNext()) {
            final Row row = iterator.next();
            return Station.from(row);
        }

        return null;
    }
}
