package pl.airq.common.util;

import java.time.OffsetDateTime;
import java.util.Objects;

public class TimestampUtil {

    private TimestampUtil() {
    }

    public static boolean isEqual(OffsetDateTime timestamp1, OffsetDateTime timestamp2) {
        if (Objects.equals(timestamp1, timestamp2)) {
            return true;
        }

        return timestamp1.toEpochSecond() == timestamp2.toEpochSecond();
    }

}
