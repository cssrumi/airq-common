package pl.airq.common.infrastructure.persistance;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import pl.airq.common.domain.PersistentRepository;

public abstract class PersistentRepositoryPostgres<T> implements PersistentRepository<T> {

    protected final PgPool client;

    protected PersistentRepositoryPostgres(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<Boolean> save(T data) {
        return client.preparedQuery(insertQuery())
                .execute(prepareTuple(data))
                .invoke(this::postSaveAction)
                .onItem().transform(this::intoBoolean)
                .invoke(result -> postProcessAction(result, data));
    }

    @Override
    public Uni<Boolean> upsert(T data) {
        return client.preparedQuery(upsertQuery())
                .execute(prepareTuple(data))
                .invoke(this::postUpsertAction)
                .onItem().transform(this::intoBoolean)
                .invoke(result -> postProcessAction(result, data));
    }

    protected boolean intoBoolean(RowSet<Row> saveResult) {
        return saveResult.rowCount() != 0;
    }

    protected abstract String insertQuery();

    protected abstract String upsertQuery();

    protected abstract Tuple prepareTuple(T data);

    protected abstract void postSaveAction(RowSet<Row> saveResult);

    protected abstract void postUpsertAction(RowSet<Row> upsertResult);

    protected abstract void postProcessAction(Boolean result, T data);
}
