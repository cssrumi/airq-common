package pl.airq.common.process;

import java.util.Objects;

public final class SneakyThrow {
    private SneakyThrow() {
    }

    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        if (Objects.nonNull(e)) {
            throw (E) e;
        }
    }
}
