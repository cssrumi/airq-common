package pl.airq.common.infrastructure.persistance;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.enriched.EnrichedData;
import pl.airq.common.domain.enriched.EnrichedDataQuery;
import pl.airq.common.vo.StationId;

@ApplicationScoped
public class EnrichedDataQueryPostgres implements EnrichedDataQuery {

    private static final String LATEST_QUERY_PART = " ORDER BY TIMESTAMP DESC LIMIT 1";
    static final String FIND_ALL_QUERY = "SELECT * FROM ENRICHED_DATA";
    static final String FIND_ALL_BY_STATION_QUERY = "SELECT * FROM ENRICHED_DATA WHERE ENRICHED_DATA.STATION = $1";
    static final String FIND_LATEST_BY_STATION_QUERY = FIND_ALL_BY_STATION_QUERY + LATEST_QUERY_PART;
    static final String FIND_ALL_COORDS_QUERY = "SELECT * FROM ENRICHED_DATA WHERE ENRICHED_DATA.LON = $1 AND ENRICHED_DATA.LAT = $2";
    static final String FIND_BY_STATION_AND_TIMESTAMP = "SELECT * FROM ENRICHED_DATA WHERE ENRICHED_DATA.STATION = $1 AND ENRICHED_DATA.TIMESTAMP = $2";
    private final PgPool client;

    @Inject
    EnrichedDataQueryPostgres(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<Set<EnrichedData>> findAll() {
        return client.query(FIND_ALL_QUERY)
                     .execute()
                     .map(this::parse);
    }

    @Override
    public Uni<Set<EnrichedData>> findAllByStation(String name) {
        return client.preparedQuery(FIND_ALL_BY_STATION_QUERY)
                     .execute(Tuple.of(name))
                     .map(this::parse);
    }

    @Override
    public Uni<EnrichedData> findLatestByStation(String name) {
        return client.preparedQuery(FIND_LATEST_BY_STATION_QUERY)
                .execute(Tuple.of(name))
                .map(this::parse)
                .map(set -> set.stream().findFirst().orElse(null));
    }

    @Override
    public Uni<Set<EnrichedData>> findAllByStationId(StationId stationId) {
        return client.preparedQuery(FIND_ALL_BY_STATION_QUERY)
                     .execute(Tuple.of(stationId.getId()))
                     .map(this::parse);
    }

    @Override
    public Uni<Set<EnrichedData>> findAllByCoords(Float lon, Float lat) {
        return client.preparedQuery(FIND_ALL_COORDS_QUERY)
                     .execute(Tuple.of(lon).addFloat(lat))
                     .map(this::parse);
    }

    @Override
    public Uni<EnrichedData> findByStationAndTimestamp(String name, OffsetDateTime timestamp) {
        return client.preparedQuery(FIND_BY_STATION_AND_TIMESTAMP)
                     .execute(Tuple.of(name).addOffsetDateTime(timestamp))
                     .map(rows -> EnrichedData.from(rows.iterator().next()));
    }

    private Set<EnrichedData> parse(RowSet<Row> pgRowSet) {
        Set<EnrichedData> enrichedData = new HashSet<>(pgRowSet.size());
        for (Row row : pgRowSet) {
            enrichedData.add(EnrichedData.from(row));
        }

        return enrichedData;
    }
}
