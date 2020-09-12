package pl.airq.common.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RegisterForReflection
public class StationLocation {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationLocation.class);
    public static final StationLocation EMPTY = new StationLocation(null, null);
    private static final String EMPTY_LOCATION_WARN = "Empty location found. Parsed to empty Location";
    private static final String DELIMITER = ",";
    private static final Map<String, Integer> COORD_MAP = Map.of("N", 1, "S", -1, "E", 1, "W", -1);

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
    public static StationLocation from(String value) {
        if (StringUtils.isEmpty(value) || StringUtils.containsOnly(value, ',')) {
            LOGGER.warn(EMPTY_LOCATION_WARN);
            return EMPTY;
        }

        final String[] split = value.split(DELIMITER);
        final float lon;
        final float lat;
        switch (split.length) {
            case 2:
                lon = Float.parseFloat(split[0].strip());
                lat = Float.parseFloat(split[1].strip());
                return new StationLocation(lon, lat);
            case 4:
                lon = COORD_MAP.get(split[1].strip()) * Float.parseFloat(split[0].strip());
                lat = COORD_MAP.get(split[3].strip()) * Float.parseFloat(split[2].strip());
                return new StationLocation(lon, lat);
            default:
                LOGGER.warn("Invalid location value: {}. Parsed to empty Location", value);
                return EMPTY;
        }
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
