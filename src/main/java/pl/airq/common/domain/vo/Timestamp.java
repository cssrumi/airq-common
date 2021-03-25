package pl.airq.common.domain.vo;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public final class Timestamp {

    private final long value;

    private Timestamp(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public OffsetDateTime asOffsetDateTime() {
        return OffsetDateTime.ofInstant(asInstant(), ZoneOffset.systemDefault());
    }

    public Instant asInstant() {
        return Instant.ofEpochSecond(value);
    }

    public static Timestamp create(long value) {
        return new Timestamp(value);
    }

    public static Timestamp create(OffsetDateTime offsetDateTime) {
        return create(offsetDateTime.toInstant());
    }

    public static Timestamp create(Instant instant) {
        return new Timestamp(instant.getEpochSecond());
    }

    public static Timestamp now() {
        return create(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timestamp timestamp = (Timestamp) o;
        return value == timestamp.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Timestamp{" +
                "value=" + value +
                '}';
    }
}
