package pl.airq.common.process.ctx.internal;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.process.Payload;
import pl.airq.common.vo.Measurement;
import pl.airq.common.vo.StationId;
import pl.airq.common.vo.StationLocation;

@RegisterForReflection
public class AirqMeasurementPayload implements Payload {

    public Measurement temperature;
    public Measurement humidity;
    public Measurement pm10;
    public Measurement pm25;
    public StationId stationId;
    public StationLocation location;

    public AirqMeasurementPayload() {
    }

    @Override
    public String toString() {
        return "AirqMeasurementPayload{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pm10=" + pm10 +
                ", pm25=" + pm25 +
                ", stationId=" + stationId +
                ", location=" + location +
                '}';
    }
}
