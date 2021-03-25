package pl.airq.common.domain.gios;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.mutiny.sqlclient.Row;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import pl.airq.common.domain.station.Station;
import pl.airq.common.util.TimestampUtil;
import pl.airq.common.domain.vo.StationId;
import pl.airq.common.domain.vo.StationLocation;

@RegisterForReflection
public class Installation {

    public final Long id;
    public final String name;
    public final OffsetDateTime timestamp;
    public final Float value;
    public final Float lon;
    public final Float lat;
    public final String code;

    private Installation(Long id, String name, OffsetDateTime timestamp, Float value, Float lon, Float lat, String code) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.value = value;
        this.lon = lon;
        this.lat = lat;
        this.code = code;
    }

    public Station intoStation() {
        return new Station(StationId.from(name), StationLocation.from(lon, lat));
    }

    public static Installation from(Row row) {
        return new Installation(row.getLong("id"), row.getString("name"),
                row.getOffsetDateTime("timestamp"), row.getFloat("value"),
                row.getFloat("y"), row.getFloat("x"),
                row.getString("code"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Installation that = (Installation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                TimestampUtil.isEqual(timestamp, that.timestamp) &&
                Objects.equals(value, that.value) &&
                Objects.equals(lon, that.lon) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timestamp, value, lon, lat, code);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Installation.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("timestamp=" + timestamp)
                .add("value=" + value)
                .add("lon=" + lon)
                .add("lat=" + lat)
                .add("code='" + code + "'")
                .toString();
    }
}
