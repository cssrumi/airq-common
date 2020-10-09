package pl.airq.common.infrastructure.query;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import pl.airq.common.domain.gios.installation.Installation;
import pl.airq.common.domain.gios.installation.InstallationQuery;
import pl.airq.common.store.key.TSFKey;

@ApplicationScoped
public class InstallationQueryPostgres implements InstallationQuery {

    private static final String WHERE = " WHERE";
    private static final String AND = " AND";
    private static final String CODE_LIKE_PM = " UPPER(code) LIKE 'PM%'";
    private static final String TIMESTAMP_BETWEEN = " \"timestamp\" BETWEEN";
    private static final String GET_ALL_QUERY = "SELECT * FROM gios.installations";
    private static final String GET_ALL_FROM_LAST_HOUR_QUERY = GET_ALL_QUERY + WHERE + CODE_LIKE_PM + AND + TIMESTAMP_BETWEEN
            + " (NOW() - INTERVAL '1 hours' ) AND NOW()";
    private static final String GET_ALL_FROM_LAST_N_HOURS_QUERY = GET_ALL_QUERY + WHERE + CODE_LIKE_PM + AND + TIMESTAMP_BETWEEN
            + " (NOW() -  ($1 || ' hours')::INTERVAL ) AND NOW()";
    private static final String GET_BY_TSFKEY_QUERY = GET_ALL_QUERY + WHERE + " \"timestamp\" = $1 AND name = $2 AND LOWER(code) = $3";
    private final PgPool client;

    @Inject
    InstallationQueryPostgres(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<Set<Installation>> getAll() {
        return client.query(GET_ALL_QUERY)
                     .execute()
                     .map(result -> DBParser.parseSet(result, Installation::from));
    }

    @Override
    public Uni<Set<Installation>> getAllWithPMSinceLastHour() {
        return client.query(GET_ALL_FROM_LAST_HOUR_QUERY)
                     .execute()
                     .map(result -> DBParser.parseSet(result, Installation::from));
    }

    @Override
    public Uni<Set<Installation>> getAllWithPMSinceLastNHours(int n) {
        return client.preparedQuery(GET_ALL_FROM_LAST_N_HOURS_QUERY)
                     .execute(Tuple.of(Integer.toString(n)))
                     .map(result -> DBParser.parseSet(result, Installation::from));
    }

    @Override
    public Uni<Installation> getByTSFKey(TSFKey key) {
        return client.preparedQuery(GET_BY_TSFKEY_QUERY)
                     .execute(key.tuple())
                     .map(result -> DBParser.parseOne(result, Installation::from));
    }
}
