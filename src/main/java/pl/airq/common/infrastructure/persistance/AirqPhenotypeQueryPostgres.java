package pl.airq.common.infrastructure.persistance;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.phenotype.AirqPhenotype;
import pl.airq.common.domain.phenotype.AirqPhenotypeQuery;
import pl.airq.common.vo.StationId;

@ApplicationScoped
public class AirqPhenotypeQueryPostgres implements AirqPhenotypeQuery {

    private static final String LATEST_QUERY_PART = " ORDER BY TIMESTAMP DESC LIMIT 1";
    static final String FIND_ALL_QUERY = "SELECT * FROM AIRQ_PHENOTYPE";
    static final String FIND_ALL_BY_STATION_ID_QUERY = "SELECT * FROM AIRQ_PHENOTYPE WHERE AIRQ_PHENOTYPE.STATIONID = $1";
    static final String FIND_LATEST_BY_STATION_ID_QUERY = FIND_ALL_BY_STATION_ID_QUERY + LATEST_QUERY_PART;
    private final PgPool client;
    private final DomainObjectMapper objectFactory;

    @Inject
    public AirqPhenotypeQueryPostgres(PgPool client, DomainObjectMapper objectFactory) {
        this.client = client;
        this.objectFactory = objectFactory;
    }

    @Override
    public Uni<Set<AirqPhenotype>> findAll() {
        return client.query(FIND_ALL_QUERY)
                     .execute()
                     .map(this::parse);
    }

    @Override
    public Uni<Set<AirqPhenotype>> findByStationId(StationId stationId) {
        return client.preparedQuery(FIND_ALL_BY_STATION_ID_QUERY)
                     .execute(Tuple.of(stationId.getId()))
                     .map(this::parse);
    }

    @Override
    public Uni<AirqPhenotype> findLatestByStationId(StationId stationId) {
        return client.preparedQuery(FIND_LATEST_BY_STATION_ID_QUERY)
                     .execute(Tuple.of(stationId.getId()))
                     .map(this::parse)
                     .map(set -> set.stream().findFirst().orElse(null));
    }

    private Set<AirqPhenotype> parse(RowSet<Row> pgRowSet) {
        Set<AirqPhenotype> airqPhenotypes = new HashSet<>(pgRowSet.size());
        for (Row row : pgRowSet) {
            objectFactory.airqPhenotype(row)
                         .ifPresent(airqPhenotypes::add);
        }

        return airqPhenotypes;
    }
}
