package pl.airq.common.domain.enriched;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.mutiny.sqlclient.Row;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import pl.airq.common.domain.DataProvider;
import pl.airq.common.util.TimestampUtil;
import pl.airq.common.domain.vo.StationId;

@RegisterForReflection
public final class EnrichedData {

    public final OffsetDateTime timestamp;
    public final Float pm10;
    public final Float pm25;
    public final Float temp;
    public final Float wind;
    public final Float windDirection;
    public final Float humidity;
    public final Float pressure;
    public final Float lon;
    public final Float lat;
    public final DataProvider provider;
    public final StationId station;


    public EnrichedData(OffsetDateTime timestamp, Float pm10, Float pm25, Float temp, Float wind, Float windDirection, Float humidity, Float pressure,
                        Float lon, Float lat, DataProvider provider, StationId station) {
        this.timestamp = timestamp;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.temp = temp;
        this.wind = wind;
        this.windDirection = windDirection;
        this.humidity = humidity;
        this.pressure = pressure;
        this.lon = lon;
        this.lat = lat;
        this.provider = provider;
        this.station = station;
    }

    public static EnrichedData from(Row row) {
        return new EnrichedData(
                row.getOffsetDateTime("timestamp"),
                row.getFloat("pm10"),
                row.getFloat("pm25"),
                row.getFloat("temperature"),
                row.getFloat("wind"),
                row.getFloat("winddirection"),
                row.getFloat("humidity"),
                row.getFloat("pressure"),
                row.getFloat("lon"),
                row.getFloat("lat"),
                DataProvider.valueOf(row.getString("provider")),
                StationId.from(row.getString("station"))
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnrichedData that = (EnrichedData) o;
        return TimestampUtil.isEqual(timestamp, that.timestamp) &&
                Objects.equals(pm10, that.pm10) &&
                Objects.equals(pm25, that.pm25) &&
                Objects.equals(temp, that.temp) &&
                Objects.equals(wind, that.wind) &&
                Objects.equals(windDirection, that.windDirection) &&
                Objects.equals(humidity, that.humidity) &&
                Objects.equals(pressure, that.pressure) &&
                Objects.equals(lon, that.lon) &&
                Objects.equals(lat, that.lat) &&
                provider == that.provider &&
                Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, pm10, pm25, temp, wind, windDirection, humidity, pressure, lon, lat, provider, station);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EnrichedData.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("pm10=" + pm10)
                .add("pm25=" + pm25)
                .add("temp=" + temp)
                .add("wind=" + wind)
                .add("windDirection=" + windDirection)
                .add("humidity=" + humidity)
                .add("pressure=" + pressure)
                .add("provider=" + provider)
                .add("station=" + station)
                .toString();
    }
}
