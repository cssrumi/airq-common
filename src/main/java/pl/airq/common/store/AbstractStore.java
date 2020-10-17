package pl.airq.common.store;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.store.layer.StoreLayer;

abstract class AbstractStore<K, V> implements Store<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStore.class);
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
                    .onItem().ifNotNull().transform(Optional::get)
                    .onItem().invokeUni(value -> upsert(key, value));
    }

    @Override
    public Uni<Set<V>> getAll() {
        return firstLayer().getAll();
    }

    @Override
    public Uni<Set<V>> getAll(Predicate<V> valuePredicate) {
        return firstLayer().getAll(valuePredicate);
    }

    @Override
    public Uni<Map<K, V>> getMap() {
        return firstLayer().getMap();
    }

    @Override
    public Uni<Map<K, V>> getMap(Predicate<K> keyPredicate) {
        return firstLayer().getMap(keyPredicate);
    }

    @Override
    public Uni<V> pull(K key) {
        return pullLayer().get(key)
                          .emitOn(executor)
                          .invokeUni(value -> upsert(key, value));
    }

    @Override
    public Uni<Set<V>> pullAll(Predicate<V> valuePredicate, Function<V, K> keyFactory) {
        return pullLayer().getAll(valuePredicate)
                          .emitOn(executor)
                          .invokeUni(vs ->
                                  Multi.createFrom().iterable(vs)
                                       .invokeUni(v -> upsert(keyFactory.apply(v), v))
                                       .collectItems().with(Collectors.counting())
                                       .invoke(count -> LOGGER.info("{} items pulled from pull layer.", count)));
    }

    @Override
    public Uni<Map<K, V>> pullMap() {
        return pullLayer().getMap()
                          .emitOn(executor)
                          .invokeUni(map -> Multi.createFrom().iterable(map.entrySet())
                                                 .invokeUni(entry -> upsert(entry.getKey(), entry.getValue()))
                                                 .collectItems().with(Collectors.counting())
                                                 .invoke(count -> LOGGER.info("{} items pulled from pull layer.", count)));
    }

    @Override
    public Uni<Map<K, V>> pullMap(Predicate<K> keyPredicate) {
        return pullLayer().getMap(keyPredicate)
                          .emitOn(executor)
                          .invokeUni(map -> Multi.createFrom().iterable(map.entrySet())
                                                 .invokeUni(entry -> upsert(entry.getKey(), entry.getValue()))
                                                 .collectItems().with(Collectors.counting())
                                                 .invoke(count -> LOGGER.info("{} items pulled from pull layer.", count)));
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

    protected StoreLayer<K, V> firstLayer() {
        return layers.get(0);
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
