package pl.airq.common.store.key;

import java.time.OffsetDateTime;
import pl.airq.common.domain.gios.installation.Installation;
import pl.airq.common.domain.station.Station;

// Timestamp Station Key
public class TSKey implements Key {

    static final String DELIMITER = "-";
    private final OffsetDateTime timestamp;
    private final String station;

    private TSKey(OffsetDateTime timestamp, String station) {
        this.timestamp = timestamp;
        this.station = station;
    }

    @Override
    public String value() {
        return String.format("%s%s%s", timestamp.toEpochSecond(), DELIMITER, station);
    }

    public static TSKey from(OffsetDateTime timestamp, String station) {
        return new TSKey(timestamp, station);
    }

    public static TSKey from(OffsetDateTime timestamp, Station station) {
        return new TSKey(timestamp, station.id.value());
    }

    public static TSKey from(Installation installation) {
        return new TSKey(installation.timestamp, installation.name);
    }
}
