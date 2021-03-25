package pl.airq.common.infrastructure.persistance;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import pl.airq.common.domain.Removable;
import pl.airq.common.infrastructure.store.key.Key;

public abstract class RemovablePostgres<T, K extends Key> implements Removable<T, K> {

    protected final PgPool client;

    protected RemovablePostgres(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<Boolean> remove(T data) {
        return client.preparedQuery(removeQuery())
                     .execute(removeTuple(data))
                     .onItem().transform(this::intoBoolean)
                     .call(result -> postProcessAction(result, data));
    }

    @Override
    public Uni<Boolean> removeByKey(K key) {
        return client.preparedQuery(removeQuery())
                     .execute(removeTuple(key))
                     .onItem().transform(this::intoBoolean)
                     .call(result -> postProcessAction(result, key));
    }

    protected boolean intoBoolean(RowSet<Row> result) {
        return result.rowCount() != 0;
    }

    protected abstract String removeQuery();

    protected abstract Tuple removeTuple(T data);

    protected abstract Tuple removeTuple(K key);

    protected abstract Uni<Void> postProcessAction(Boolean isSuccess, T data);

    protected abstract Uni<Void> postProcessAction(Boolean isSuccess, K key);
}
