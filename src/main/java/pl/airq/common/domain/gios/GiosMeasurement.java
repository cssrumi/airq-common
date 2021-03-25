package pl.airq.common.domain.gios;

import com.google.common.base.Preconditions;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;
import org.apache.commons.lang3.StringUtils;
import pl.airq.common.domain.station.Station;
import pl.airq.common.util.TimestampUtil;

@RegisterForReflection
public class GiosMeasurement {

    public final OffsetDateTime timestamp;
    public final Station station;
    public final Float pm10;
    public final Float pm25;

    public GiosMeasurement(OffsetDateTime timestamp, Station station, Float pm10, Float pm25) {
        this.timestamp = timestamp;
        this.station = station;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }

    public GiosMeasurement merge(Installation installation) {
        final Float newPm10 = getPm10(installation);
        final Float newPm25 = getPm25(installation);
        return new GiosMeasurement(timestamp, station, newPm10 != null ? newPm10 : pm10, newPm25 != null ? newPm25 : pm25);
    }

    public GiosMeasurement remove(Installation installation) {
        switch (installation.code.toLowerCase()) {
            case "pm10":
                return new GiosMeasurement(timestamp, station, null, pm25);
            case "pm2.5":
            case "pm25":
                return new GiosMeasurement(timestamp, station, pm10, null);
            default:
                return this;
        }
    }

    public static GiosMeasurement from(Installation installation) {
        return new GiosMeasurement(installation.timestamp, installation.intoStation(), getPm10(installation), getPm25(installation));
    }

    public static GiosMeasurement from(Installation... installations) {
        Preconditions.checkNotNull(installations);
        Preconditions.checkArgument(installations.length > 0);
        final Iterator<Installation> iterator = Arrays.stream(installations).iterator();
        GiosMeasurement giosMeasurement = from(iterator.next());
        while (iterator.hasNext()) {
            giosMeasurement = giosMeasurement.merge(iterator.next());
        }
        return giosMeasurement;
    }

    private static Float getPm10(Installation installation) {
        return "PM10".equals(StringUtils.upperCase(installation.code)) ? installation.value : null;
    }

    private static Float getPm25(Installation installation) {
        return isPm25(installation.code) ? installation.value : null;
    }

    private static boolean isPm25(String code) {
        String upperCode = StringUtils.upperCase(code);
        return "PM2.5".equals(upperCode) || "PM25".equals(upperCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GiosMeasurement that = (GiosMeasurement) o;
        return TimestampUtil.isEqual(timestamp, that.timestamp) &&
                Objects.equals(station, that.station) &&
                Objects.equals(pm10, that.pm10) &&
                Objects.equals(pm25, that.pm25);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, station, pm10, pm25);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GiosMeasurement.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("station=" + station)
                .add("pm10=" + pm10)
                .add("pm25=" + pm25)
                .toString();
    }
}
