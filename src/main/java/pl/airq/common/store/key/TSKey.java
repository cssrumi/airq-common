package pl.airq.common.store.key;

import java.time.OffsetDateTime;
import java.util.Objects;
import pl.airq.common.domain.gios.installation.Installation;
import pl.airq.common.domain.station.Station;

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

    public static TSKey from(OffsetDateTime timestamp, String station) {
        return new TSKey(timestamp, station, computeValue(timestamp, station));
    }

    public static TSKey from(OffsetDateTime timestamp, Station station) {
        return new TSKey(timestamp, station.id.value(), computeValue(timestamp, station.id.value()));
    }

    public static TSKey from(Installation installation) {
        return new TSKey(installation.timestamp, installation.name, computeValue(installation.timestamp, installation.name));
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
