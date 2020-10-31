package pl.airq.common.config.properties;

import java.time.temporal.ChronoUnit;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ExpireIn {

    @NotNull
    private ChronoUnit timeUnit;
    @Min(1)
    private long in;

    public ChronoUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(ChronoUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public long getIn() {
        return in;
    }

    public void setIn(long in) {
        this.in = in;
    }
}
