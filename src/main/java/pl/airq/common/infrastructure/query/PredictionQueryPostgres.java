package pl.airq.common.infrastructure.query;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.prediction.Prediction;
import pl.airq.common.domain.prediction.PredictionConfig;
import pl.airq.common.domain.prediction.PredictionQuery;
import pl.airq.common.infrastructure.persistance.DomainObjectMapper;
import pl.airq.common.domain.vo.StationId;

@ApplicationScoped
public class PredictionQueryPostgres implements PredictionQuery {

    private static final String LATEST_QUERY_PART = " ORDER BY TIMESTAMP DESC LIMIT 1";
    private static final String CONFIG_QUERY_PART = " WHERE CAST ( CONFIG ->> 'timeFrame' AS INTEGER) = $2" +
            "AND CONFIG ->> 'timeUnit' = $3 AND CONFIG ->> 'field' = $4";
    static final String FIND_ALL_QUERY = "SELECT * FROM PREDICTION WHERE STATIONID = $1";
    static final String FIND_LATEST_QUERY = FIND_ALL_QUERY + LATEST_QUERY_PART;
    static final String FIND_LATEST_WITH_PREDICTION_CONFIG_QUERY = FIND_ALL_QUERY + CONFIG_QUERY_PART + LATEST_QUERY_PART;

    private final PgPool client;
    private final DomainObjectMapper objectFactory;

    @Inject
    public PredictionQueryPostgres(PgPool client, DomainObjectMapper objectFactory) {
        this.client = client;
        this.objectFactory = objectFactory;
    }

    @Override
    public Uni<Prediction> findLatest(StationId stationId) {
        return client.preparedQuery(FIND_LATEST_QUERY)
                     .execute(findLatestTuple(stationId))
                     .map(result -> DBParser.parseOptional(result, objectFactory::prediction));
    }

    @Override
    public Uni<Set<Prediction>> findAll(StationId stationId) {
        return client.preparedQuery(FIND_ALL_QUERY)
                     .execute(findAllTuple(stationId))
                     .map(result -> DBParser.parseOptionalSet(result, objectFactory::prediction));
    }

    @Override
    public Uni<Prediction> findLatestWithPredictionConfig(StationId stationId, PredictionConfig config) {
        return client.preparedQuery(FIND_LATEST_WITH_PREDICTION_CONFIG_QUERY)
                     .execute(findLatestWithPredictionConfigTuple(stationId, config))
                     .map(result -> DBParser.parseOptional(result, objectFactory::prediction));
    }

    private Tuple findAllTuple(StationId stationId) {
        return Tuple.of(stationId.value());
    }

    private Tuple findLatestTuple(StationId stationId) {
        return Tuple.of(stationId.value());
    }

    private Tuple findLatestWithPredictionConfigTuple(StationId stationId, PredictionConfig config) {
        return Tuple.of(stationId.value())
                    .addLong(config.timeframe)
                    .addString(config.timeUnit.name())
                    .addString(config.field);
    }
}
