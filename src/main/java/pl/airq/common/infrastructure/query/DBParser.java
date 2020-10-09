package pl.airq.common.infrastructure.query;

import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowIterator;
import io.vertx.mutiny.sqlclient.RowSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

class DBParser {

    private DBParser() {
    }

    static <T> Set<T> parseSet(RowSet<Row> pgRowSet, Function<Row, T> parseFunction) {
        Set<T> set = new HashSet<>(pgRowSet.size());
        for (Row row : pgRowSet) {
            set.add(parseFunction.apply(row));
        }

        return set;
    }

    static <T> Set<T> parseOptionalSet(RowSet<Row> pgRowSet, Function<Row, Optional<T>> parseOptionalFunction) {
        Set<T> set = new HashSet<>(pgRowSet.size());
        for (Row row : pgRowSet) {
            parseOptionalFunction.apply(row)
                                 .ifPresent(set::add);
        }

        return set;
    }

    static <T> T parseOne(RowSet<Row> pgRowSet, Function<Row, T> parseFunction) {
        final RowIterator<Row> iterator = pgRowSet.iterator();
        if (iterator.hasNext()) {
            final Row row = iterator.next();
            return parseFunction.apply(row);
        }

        return null;
    }

    static <T> T parseOptional(RowSet<Row> pgRowSet, Function<Row, Optional<T>> parseFunction) {
        final RowIterator<Row> iterator = pgRowSet.iterator();
        if (iterator.hasNext()) {
            final Row row = iterator.next();
            return parseFunction.apply(row)
                                .orElse(null);
        }

        return null;
    }
}
