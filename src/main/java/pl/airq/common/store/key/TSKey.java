package pl.airq.common.store.key;

import com.google.common.base.Preconditions;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import pl.airq.common.domain.gios.GiosMeasurement;
import pl.airq.common.domain.gios.Installation;
import pl.airq.common.domain.station.Station;
import pl.airq.common.vo.StationId;

// Timestamp Station Key
public class TSKey implements Key {

    private final OffsetDateTime timestamp;
    private final String station;
    private final String value;

    private TSKey(OffsetDateTime timestamp, String station, String value) {
        this.timestamp = timestamp;
        this.station = station;
        this.value = value;
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

    public StationId stationId() {
        return StationId.from(station);
    }

    public static TSKey from(String value) {
        Preconditions.checkNotNull(value,
                String.format(EMPTY_ARG_MESSAGE_TEMPLATE, TSKey.class.getSimpleName(), "value"));
        final String[] split = value.split(DEFAULT_DELIMITER);
        Preconditions.checkArgument(split.length == 2,
                String.format("%s value is invalid: %s", TSKey.class.getSimpleName(), value));
        OffsetDateTime timestamp = OffsetDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(split[0])), ZoneOffset.systemDefault());
        String station = split[1];

        return new TSKey(timestamp, station, value);
    }

    public static TSKey from(OffsetDateTime timestamp, String station) {
        return new TSKey(timestamp, station, computeValue(timestamp, station));
    }

    public static TSKey from(OffsetDateTime timestamp, Station station) {
        return new TSKey(timestamp, station.id.value(), computeValue(timestamp, station.id.value()));
    }

    public static TSKey from(Installation installation) {
        return new TSKey(installation.timestamp, installation.name, computeValue(installation.timestamp, installation.name));
    }

    public static TSKey from(GiosMeasurement measurement) {
        return new TSKey(measurement.timestamp, measurement.station.id.value(), computeValue(measurement.timestamp, measurement.station.id.value()));
    }

    private static String computeValue(OffsetDateTime timestamp, String station) {
        return String.format("%s%s%s", timestamp.toEpochSecond(), DEFAULT_DELIMITER, station);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TSKey tsKey = (TSKey) o;
        return value.equals(tsKey.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "TSKey{" +
                "value='" + value + '\'' +
                '}';
    }
}
