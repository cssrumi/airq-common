package pl.airq.common.infrastructure.persistance;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlClientHelper;
import io.vertx.mutiny.sqlclient.Tuple;
import org.apache.commons.lang3.BooleanUtils;
import pl.airq.common.domain.PersistentRepository;
import pl.airq.common.infrastructure.query.DBParser;

public abstract class PersistentRepositoryPostgres<T> implements PersistentRepository<T> {

    protected final PgPool client;

    protected PersistentRepositoryPostgres(PgPool client) {
        this.client = client;
    }

    @Override
    public Uni<Boolean> save(T data) {
        return client.preparedQuery(insertQuery())
                     .execute(insertTuple(data))
                     .onItem().transform(this::intoBoolean)
                     .invoke(result -> postProcessAction(Action.SAVE, result, data));
    }

    @Override
    public Uni<Boolean> upsert(T data) {
        return SqlClientHelper.inTransactionUni(client, tx -> {
            Uni<Boolean> isAlreadyExist = tx.preparedQuery(isAlreadyExistQuery())
                                            .execute(isAlreadyExistTuple(data))
                                            .map(result -> DBParser.parseOne(result, this::isAlreadyExistParser));
            Uni<RowSet<Row>> insert = tx.preparedQuery(insertQuery())
                                        .execute(insertTuple(data));
            Uni<RowSet<Row>> update = tx.preparedQuery(updateQuery())
                                        .execute(updateTuple(data));
            return isAlreadyExist.onItem().transformToUni(isExist -> BooleanUtils.isNotTrue(isExist) ? insert : update);
        })
                              .onItem().transform(this::intoBoolean)
                              .call(result -> postProcessAction(Action.UPSERT, result, data));
    }

    protected boolean intoBoolean(RowSet<Row> saveResult) {
        return saveResult.rowCount() != 0;
    }

    protected Boolean isAlreadyExistParser(Row row) {
        return row.getBoolean(1);
    }

    protected abstract String insertQuery();

    protected abstract String updateQuery();

    protected abstract String isAlreadyExistQuery();

    protected abstract Tuple insertTuple(T data);

    protected abstract Tuple updateTuple(T data);

    protected abstract Tuple isAlreadyExistTuple(T data);

    protected abstract Uni<Void> postProcessAction(Action action, Boolean isSuccess, T data);

    public enum Action {
        SAVE, UPSERT;
    }
}
