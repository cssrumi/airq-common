package pl.airq.common.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

@RegisterForReflection
public class Measurement {

    public static final Measurement EMPTY = new Measurement(null);
    private final Float value;

    private Measurement(Float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public Double getDoubleValue() {
        return value != null ? value.doubleValue() : null;
    }

    @JsonCreator
    public static Measurement from(Float value) {
        return value != null ? new Measurement(value) : EMPTY;
    }

    @JsonCreator
    public static Measurement fromDouble(Double value) {
        return value != null ? new Measurement(value.floatValue()) : EMPTY;
    }

    @JsonCreator
    public static Measurement fromInteger(Integer value) {
        return value != null ? new Measurement(Float.valueOf(value)) : EMPTY;
    }

    @JsonCreator
    public static Measurement fromString(String value) {
        if (StringUtils.isEmpty(value)) {
            return EMPTY;
        }

        final String strippedValue = value.strip().replace("%", "").replace("°C", "");
        if (StringUtils.isNumeric(strippedValue)) {
            return new Measurement(Float.valueOf(strippedValue));
        }

        return EMPTY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Measurement that = (Measurement) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                '}';
    }
}
