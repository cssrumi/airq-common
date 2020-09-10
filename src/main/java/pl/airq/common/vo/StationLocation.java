package pl.airq.common.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RegisterForReflection
public class StationLocation {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationLocation.class);
    private static final StationLocation EMPTY = new StationLocation(null, null);
    private static final String EMPTY_LOCATION_WARN = "Empty location found. Parsed to empty Location";
    private static final String DELIMITER = ",";

    private final Float lon;
    private final Float lat;

    private StationLocation(Float lon, Float lat) {
        this.lon = lon;
        this.lat = lat;
    }

    @JsonIgnore
    public String getLocation() {
        return String.format("%s%s%s", lon, DELIMITER, lat);
    }

    public Float getLon() {
        return lon;
    }

    public Float getLat() {
        return lat;
    }

    @JsonCreator
    public static StationLocation from(@JsonProperty("location") String value) {
        if (StringUtils.isEmpty(value) || StringUtils.containsOnly(value, ',')) {
            LOGGER.warn(EMPTY_LOCATION_WARN);
            return EMPTY;
        }

        final String[] split = value.split(DELIMITER);
        if (split.length != 2) {
            LOGGER.warn("Invalid location value: {}. Parsed to empty Location", value);
            return EMPTY;
        }

        final Float lon = Float.valueOf(split[0].strip());
        final Float lat = Float.valueOf(split[1].strip());
        return new StationLocation(lon, lat);
    }

    public static StationLocation from(String lon, String lat) {
        if (StringUtils.isEmpty(lon) || StringUtils.isEmpty(lat)) {
            LOGGER.warn(EMPTY_LOCATION_WARN);
            return EMPTY;
        }

        return new StationLocation(Float.valueOf(lon), Float.valueOf(lat));
    }

    public static StationLocation from(Float lon, Float lat) {
        if (Objects.isNull(lon) || Objects.isNull(lat)) {
            LOGGER.warn(EMPTY_LOCATION_WARN);
            return EMPTY;
        }

        return new StationLocation(lon, lat);
    }

    @Override
    public String toString() {
        return "StationLocation{" +
                "longitude='" + lon + '\'' +
                "latitude='" + lat + '\'' +
                '}';
    }
}
