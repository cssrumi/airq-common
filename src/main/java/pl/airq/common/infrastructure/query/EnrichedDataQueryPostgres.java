package pl.airq.common.infrastructure.query;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.time.OffsetDateTime;
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
    static final String FIND_ALL_BY_COORDS_QUERY = "SELECT * FROM ENRICHED_DATA WHERE ENRICHED_DATA.LON = $1 AND ENRICHED_DATA.LAT = $2";
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
                     .map(result -> DBParser.parseSet(result, EnrichedData::from));
    }

    @Override
    public Uni<Set<EnrichedData>> findAllByStation(String name) {
        return client.preparedQuery(FIND_ALL_BY_STATION_QUERY)
                     .execute(Tuple.of(name))
                     .map(result -> DBParser.parseSet(result, EnrichedData::from));
    }

    @Override
    public Uni<EnrichedData> findLatestByStation(String name) {
        return client.preparedQuery(FIND_LATEST_BY_STATION_QUERY)
                     .execute(Tuple.of(name))
                     .map(result -> DBParser.parseOne(result, EnrichedData::from));
    }

    @Override
    public Uni<Set<EnrichedData>> findAllByStationId(StationId stationId) {
        return client.preparedQuery(FIND_ALL_BY_STATION_QUERY)
                     .execute(Tuple.of(stationId.value()))
                     .map(result -> DBParser.parseSet(result, EnrichedData::from));
    }

    @Override
    public Uni<Set<EnrichedData>> findAllByCoords(Float lon, Float lat) {
        return client.preparedQuery(FIND_ALL_BY_COORDS_QUERY)
                     .execute(Tuple.of(lon).addFloat(lat))
                     .map(result -> DBParser.parseSet(result, EnrichedData::from));
    }

    @Override
    public Uni<EnrichedData> findByStationAndTimestamp(String name, OffsetDateTime timestamp) {
        return client.preparedQuery(FIND_BY_STATION_AND_TIMESTAMP)
                     .execute(Tuple.of(name).addOffsetDateTime(timestamp))
                     .map(result -> DBParser.parseOne(result, EnrichedData::from));
    }
}
