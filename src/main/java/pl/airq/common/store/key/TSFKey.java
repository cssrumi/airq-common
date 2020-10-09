package pl.airq.common.store.key;

import com.google.common.base.Preconditions;
import io.vertx.mutiny.sqlclient.Tuple;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import pl.airq.common.domain.gios.installation.Installation;
import pl.airq.common.domain.station.Station;

// Timestamp Station Field Key
public class TSFKey implements Key {

    static final String DELIMITER = "-";
    public final OffsetDateTime timestamp;
    public final String station;
    public final String field;

    private TSFKey(@NotNull OffsetDateTime timestamp,@NotNull String station,@NotNull String field) {
        Preconditions.checkArgument(timestamp != null, String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "timestamp"));
        Preconditions.checkArgument(station != null, String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "station"));
        Preconditions.checkArgument(field != null, String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "field"));
        this.timestamp = timestamp;
        this.station = station;
        this.field = field;
    }

    @Override
    public String value() {
        return String.format("%s%s%s%s%s", timestamp.toEpochSecond(), DELIMITER, station, DELIMITER, field);
    }

    public Tuple tuple() {
        return Tuple.of(timestamp).addString(station).addString(field);
    }

    public static TSFKey from(OffsetDateTime timestamp, String station, String field) {
        return new TSFKey(timestamp, station, field);
    }

    public static TSFKey from(OffsetDateTime timestamp, Station station, String field) {
        return new TSFKey(timestamp, station.id.value(), field);
    }

    public static TSFKey from(Installation installation) {
        return new TSFKey(installation.timestamp, installation.name, installation.code.toLowerCase());
    }
}
