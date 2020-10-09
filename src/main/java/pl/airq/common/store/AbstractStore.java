package pl.airq.common.store;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import javax.validation.constraints.NotNull;
import pl.airq.common.store.layer.StoreLayer;

abstract class AbstractStore<K, V> implements Store<K, V> {

    protected final List<StoreLayer<K, V>> layers;
    protected final ExecutorService executor;

    protected AbstractStore(@NotNull List<StoreLayer<K, V>> layers, @NotNull ExecutorService executor) {
        Preconditions.checkArgument(!Iterables.isEmpty(layers), "Layers can't be empty");
        Preconditions.checkArgument(Objects.nonNull(executor), "Executor can't be null");
        this.layers = layers;
        this.executor = executor;
    }

    @Override
    public Uni<V> get(K key) {
        return Multi.createFrom().iterable(layers)
                    .onItem().transformToUni(layer -> layer.get(key).map(Optional::ofNullable))
                    .concatenate().filter(Optional::isPresent).toUni()
                    .onItem().transform(Optional::get)
                    .onItem().ifNotNull().invoke(value -> upsert(key, value));
    }

    @Override
    public Uni<V> pull(K key) {
        return pullLayer().get(key)
                          .onItem().invoke(value -> upsert(key, value));
    }

    @Override
    public Uni<V> upsert(K key, V value) {
        return value == null ? delete(key).map(ignore -> value) :
                propagateAll(layer -> layer.upsert(key, value), PropagationOrder.TO_LOWER)
                        .collectItems().asList()
                        .onItem().transform(ignore -> value);
    }

    @Override
    public Uni<Void> delete(K key) {
        return propagateAll(layer -> layer.delete(key), PropagationOrder.TO_LOWER)
                .collectItems().asList()
                .onItem().transform(this::toVoid);
    }

    public <T> T intoBlocking(Uni<T> uni, Duration maxAwaitTime) {
        return uni.runSubscriptionOn(executor)
                  .await().atMost(maxAwaitTime);
    }

    protected StoreLayer<K, V> lastLayer() {
        return Iterables.getLast(layers);
    }

    protected Void toVoid(Object ignore) {
        return null;
    }

    protected <T> Multi<T> propagateAll(Function<StoreLayer<K, V>, Uni<T>> function, PropagationOrder order) {
        return order.flat(Multi.createFrom().iterable(order.prepare(layers))
                               .onItem().transformToUni(function));
    }

    protected abstract StoreLayer<K, V> pullLayer();
}
