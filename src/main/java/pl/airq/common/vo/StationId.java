package pl.airq.common.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkus.runtime.util.StringUtil;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

@RegisterForReflection
public class StationId {

    public static final StationId EMPTY = new StationId("");

    private final String id;

    private StationId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @JsonCreator
    public static StationId from(String value) {
        return StringUtils.isEmpty(value) ? EMPTY : new StationId(value);
    }

    @Override
    public String toString() {
        return "StationId{" +
                "id='" + id + '\'' +
                '}';
    }

}
