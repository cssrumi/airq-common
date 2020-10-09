package pl.airq.common.store.key;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import pl.airq.common.domain.exception.ProcessingException;
import pl.airq.common.vo.StationLocation;

// Timestamp Location Key
public class TLKey implements Key {

    static final String DELIMITER = "-";
    private final OffsetDateTime timestamp;
    private final StationLocation location;

    private TLKey(OffsetDateTime timestamp, StationLocation location) {
        this.timestamp = timestamp;
        this.location = location;
    }

    @Override
    public String value() {
        return String.format("%s%s%s", timestamp.toEpochSecond(), DELIMITER, location.getLocation());
    }

    public static TLKey from(String key) {
        final String[] keyParts = Optional.ofNullable(key).map(k -> k.split(DELIMITER)).orElse(null);
        if (keyParts == null || keyParts.length != 2) {
            throw new ProcessingException("Unable to create " + TLKey.class.getSimpleName() + " from: " + key);
        }
        OffsetDateTime timestamp = OffsetDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(keyParts[0])), ZoneOffset.UTC);
        StationLocation location = StationLocation.from(keyParts[1]);

        return new TLKey(timestamp, location);
    }

    public static TLKey from(OffsetDateTime timestamp, StationLocation location) {
        return new TLKey(timestamp, location);
    }
}
