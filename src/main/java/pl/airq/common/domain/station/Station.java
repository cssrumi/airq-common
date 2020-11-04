package pl.airq.common.domain.station;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.vertx.mutiny.sqlclient.Row;
import java.util.StringJoiner;
import pl.airq.common.vo.StationId;
import pl.airq.common.vo.StationLocation;

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
    public String toString() {
        return new StringJoiner(", ", Station.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("location=" + location)
                .toString();
    }
}
