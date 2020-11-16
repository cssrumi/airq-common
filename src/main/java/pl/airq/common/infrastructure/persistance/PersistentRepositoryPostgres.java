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
    public Uni<Result> save(T data) {
        return client.preparedQuery(insertQuery())
                     .execute(insertTuple(data))
                     .onItem().transform(dbResult -> intoResult(dbResult, Result.SAVED))
                     .invoke(result -> postProcessAction(result, data));
    }

    @Override
    public Uni<Result> upsert(T data) {
        return SqlClientHelper.inTransactionUni(client, tx -> {
            Uni<Boolean> isAlreadyExist = tx.preparedQuery(isAlreadyExistQuery())
                                            .execute(isAlreadyExistTuple(data))
                                            .map(result -> DBParser.parseOne(result, this::isAlreadyExistParser));
            Uni<Result> insert = tx.preparedQuery(insertQuery())
                                   .execute(insertTuple(data))
                                   .map(dbResult -> intoResult(dbResult, Result.SAVED));
            Uni<Result> update = tx.preparedQuery(updateQuery())
                                   .execute(updateTuple(data))
                                   .map(dbResult -> intoResult(dbResult, Result.UPSERTED));

            return isAlreadyExist.onItem().transformToUni(isExist -> BooleanUtils.isNotTrue(isExist) ? insert : update);
        }).call(result -> postProcessAction(result, data));
    }

    protected Result intoResult(RowSet<Row> dbResult, Result expected) {
        return dbResult.rowCount() != 0 ? expected : Result.FAILED;
    }

    protected Boolean isAlreadyExistParser(Row row) {
        return row.getBoolean(0);
    }

    protected abstract String insertQuery();

    protected abstract String updateQuery();

    protected abstract String isAlreadyExistQuery();

    protected abstract Tuple insertTuple(T data);

    protected abstract Tuple updateTuple(T data);

    protected abstract Tuple isAlreadyExistTuple(T data);

    protected abstract Uni<Void> postProcessAction(Result result, T data);

    public static class Default {
        public static final String NEVER_EXIST_QUERY = "SELECT 1 WHERE 1 = $1";
        public static final Tuple NEVER_EXIST_TUPLE = Tuple.of(0);
    }
}
