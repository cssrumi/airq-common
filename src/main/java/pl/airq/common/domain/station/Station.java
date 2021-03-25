package pl.airq.common.domain.station;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.mutiny.sqlclient.Row;
import java.util.Objects;
import java.util.StringJoiner;
import pl.airq.common.domain.vo.StationId;
import pl.airq.common.domain.vo.StationLocation;

@RegisterForReflection
public class Station {

    public final StationId id;
    public final StationLocation location;

    public Station(StationId id, StationLocation location) {
        this.id = id;
        this.location = location;
    }

    public static Station from(Row row) {
        return new Station(
                StationId.from(row.getString("station")),
                StationLocation.from(row.getFloat("lon"), row.getFloat("lat"))
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
        Station station = (Station) o;
        return Objects.equals(id, station.id) &&
                Objects.equals(location, station.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Station.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("location=" + location)
                .toString();
    }
}
