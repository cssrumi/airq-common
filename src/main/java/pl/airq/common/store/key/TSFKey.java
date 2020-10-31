package pl.airq.common.store.key;

import com.google.common.base.Preconditions;
import io.vertx.mutiny.sqlclient.Tuple;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import pl.airq.common.domain.gios.Installation;
import pl.airq.common.domain.station.Station;

// Timestamp Station Field Key
public class TSFKey implements Key {

    private final OffsetDateTime timestamp;
    private final String station;
    private final String field;
    private final String value;

    private TSFKey(OffsetDateTime timestamp, String station, String field, String value) {
        this.timestamp = timestamp;
        this.station = station;
        this.field = field;
        this.value = value;
    }

    private TSFKey(OffsetDateTime timestamp, String station, String field) {
        this(timestamp, station, field, computeValue(timestamp, station, field));
    }

    @Override
    public String value() {
        return value;
    }

    public OffsetDateTime timestamp() {
        return timestamp;
    }

    public String station() {
        return station;
    }

    public String field() {
        return field;
    }

    public Tuple tuple() {
        return Tuple.of(timestamp).addString(station).addString(field);
    }

    public static TSFKey from(String value) {
        Preconditions.checkNotNull(value,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "value"));
        final String[] split = value.split(DEFAULT_DELIMITER);
        Preconditions.checkArgument(split.length == 3,
                String.format("%s value is invalid: %s", TSFKey.class.getSimpleName(), value));
        OffsetDateTime timestamp = OffsetDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(split[0])), ZoneOffset.systemDefault());
        String station = split[1];
        String field = split[2];

        return new TSFKey(timestamp, station, field, value);
    }

    public static TSFKey from(OffsetDateTime timestamp, String station, String field) {
        Preconditions.checkNotNull(timestamp,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "timestamp"));
        Preconditions.checkNotNull(station,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "station"));
        Preconditions.checkNotNull(field,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "field"));
        return new TSFKey(timestamp, station, field);
    }

    public static TSFKey from(OffsetDateTime timestamp, Station station, String field) {
        Preconditions.checkNotNull(timestamp,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "timestamp"));
        Preconditions.checkNotNull(station,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "station"));
        Preconditions.checkNotNull(field,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSFKey.class.getSimpleName(), "field"));
        return new TSFKey(timestamp, station.id.value(), field);
    }

    public static TSFKey from(Installation installation) {
        Preconditions.checkNotNull(installation,
                String.format("Can not create {} from null Installation", TSFKey.class.getSimpleName()));
        return new TSFKey(installation.timestamp, installation.name, installation.code.toLowerCase());
    }

    private static String computeValue(OffsetDateTime timestamp, String station, String field) {
        return String.format("%s%s%s%s%s", timestamp.toEpochSecond(), DEFAULT_DELIMITER, station, DEFAULT_DELIMITER, field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TSFKey key = (TSFKey) o;
        return value.equals(key.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "TSFKey{" +
                "value='" + value + '\'' +
                '}';
    }
}
