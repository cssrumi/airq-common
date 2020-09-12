package pl.airq.common.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.lang3.StringUtils;

@RegisterForReflection
public class Measurement {

    private static final Measurement EMPTY = new Measurement(null);
    private final Float value;

    private Measurement(Float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public Double getDoubleValue() {
        return value.doubleValue();
    }

    @JsonCreator
    public static Measurement from(@JsonProperty("value") Float value) {
        return value != null ? new Measurement(value) : EMPTY;
    }

    @JsonCreator
    public static Measurement fromDouble(@JsonProperty("value") Double value) {
        return value != null ? new Measurement(value.floatValue()) : EMPTY;
    }

    @JsonCreator
    public static Measurement fromInteger(@JsonProperty("value") Integer value) {
        return value != null ? new Measurement(Float.valueOf(value)) : EMPTY;
    }

    @JsonCreator
    public static Measurement fromString(@JsonProperty("value") String value) {
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
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                '}';
    }
}
