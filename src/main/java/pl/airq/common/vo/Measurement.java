package pl.airq.common.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Measurement {

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
        return new Measurement(value);
    }

    @JsonCreator
    public static Measurement fromDouble(@JsonProperty("value") Double value) {
        return new Measurement(value.floatValue());
    }

    @JsonCreator
    public static Measurement fromInteger(@JsonProperty("value") Integer value) {
        return new Measurement(Float.valueOf(value));
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "value=" + value +
                '}';
    }
}
