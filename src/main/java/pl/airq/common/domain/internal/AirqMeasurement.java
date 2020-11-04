package pl.airq.common.domain.internal;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import java.util.StringJoiner;
import pl.airq.common.vo.Measurement;
import pl.airq.common.vo.StationId;
import pl.airq.common.vo.StationLocation;

@RegisterForReflection
public final class AirqMeasurement {

    public final OffsetDateTime timestamp;
    public final Measurement temperature;
    public final Measurement humidity;
    public final Measurement pm10;
    public final Measurement pm25;
    public final StationId stationId;
    public final StationLocation location;

    public AirqMeasurement(OffsetDateTime timestamp, Measurement temperature, Measurement humidity, Measurement pm10,
                           Measurement pm25, StationId stationId, StationLocation location) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.stationId = stationId;
        this.location = location;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AirqMeasurement.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("temperature=" + temperature)
                .add("humidity=" + humidity)
                .add("pm10=" + pm10)
                .add("pm25=" + pm25)
                .add("stationId=" + stationId)
                .add("location=" + location)
                .toString();
    }
}
