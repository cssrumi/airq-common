package pl.airq.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

@RegisterForReflection
public class StationId {

    public static final StationId EMPTY = new StationId("");

    private final String value;

    private StationId(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @JsonCreator
    public static StationId from(String value) {
        return StringUtils.isEmpty(value) ? EMPTY : new StationId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StationId stationId = (StationId) o;
        return Objects.equals(value, stationId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "StationId{" +
                "value='" + value + '\'' +
                '}';
    }

}
