package pl.airq.common.process;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class MutinyUtils {

    public static Uni<Void> ignoreUniResult(Object obj) {
        return Uni.createFrom().voidItem();
    }

    public static Multi<Void> ignoreMultiResult(Object obj) {
        return Multi.createFrom().empty();
    }

    public static Uni<Void> uniFromRunnable(Runnable runnable) {
        return Uni.createFrom().voidItem().invoke(ignore -> runnable.run());
    }
}
