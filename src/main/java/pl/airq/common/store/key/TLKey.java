package pl.airq.common.store.key;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;
import pl.airq.common.exception.ProcessingException;
import pl.airq.common.vo.StationLocation;

// Timestamp Location Key
public class TLKey implements Key {

    private final OffsetDateTime timestamp;
    private final StationLocation location;
    private final String value;

    private TLKey(OffsetDateTime timestamp, StationLocation location, String value) {
        this.timestamp = timestamp;
        this.location = location;
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public OffsetDateTime timestamp() {
        return timestamp;
    }

    public StationLocation location() {
        return location;
    }

    public static TLKey from(String key) {
        final String[] keyParts = Optional.ofNullable(key).map(k -> k.split(DEFAULT_DELIMITER)).orElse(null);
        if (keyParts == null || keyParts.length != 2) {
            throw new ProcessingException("Unable to create " + TLKey.class.getSimpleName() + " from: " + key);
        }
        OffsetDateTime timestamp = OffsetDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(keyParts[0])), ZoneOffset.UTC);
        StationLocation location = StationLocation.from(keyParts[1]);
        String value = computeValue(timestamp, location);

        return new TLKey(timestamp, location, value);
    }

    public static TLKey from(OffsetDateTime timestamp, StationLocation location) {
        return new TLKey(timestamp, location, computeValue(timestamp, location));
    }

    public static String computeValue(OffsetDateTime timestamp, StationLocation location) {
        return String.format("%s%s%s", timestamp.toEpochSecond(), DEFAULT_DELIMITER, location.getLocation());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TLKey tlKey = (TLKey) o;
        return value.equals(tlKey.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "TLKey{" +
                "value='" + value + '\'' +
                '}';
    }
}
