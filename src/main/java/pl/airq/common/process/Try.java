package pl.airq.common.process;

import io.smallrye.mutiny.Uni;
import java.util.Optional;
import java.util.function.Supplier;

import static pl.airq.common.process.SneakyThrow.sneakyThrow;

public final class Try<R> {

    private final R value;
    private final Throwable error;

    private Try(R value, Throwable error) {
        this.value = value;
        this.error = error;
    }

    public R get() {
        sneakyThrow(error);
        return value;
    }

    public R orElse(R value) {
        return error == null ? this.value : value;
    }

    public R orElseGet(Supplier<R> supplier) {
        return error == null ? value : supplier.get();
    }

    public Optional<R> optional() {
        return Optional.ofNullable(get());
    }

    public Optional<R> suppressed() {
        return error == null ? Optional.ofNullable(value) : Optional.empty();
    }

    public Throwable error() {
        return error;
    }

    public static <T> Uni<Try<T>> from(Uni<T> uni) {
        return uni.onItem().transform(e -> of(() -> e))
                  .onFailure().recoverWithItem(Try::from);
    }

    public static <T> Try<T> from(Throwable throwable) {
        return new Try<T>(null, throwable);
    }

    public static Uni<Try> raw(Uni uni) {
        return from(uni);
    }

    public static Try raw(Throwable throwable) {
        return new Try(null, throwable);
    }

    public static <T> Try<T> of(Supplier<T> supplier) {
        T response = null;
        Throwable throwable = null;
        try {
            response = supplier.get();
        } catch (Throwable t) {
            throwable = t;
        }

        return new Try<T>(response, throwable);
    }
}
