package pl.airq.common.store.layer;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import pl.airq.common.process.MutinyUtils;

public class GuavaCacheLayer<K, V> implements StoreLayer<K, V> {

    protected final Cache<K, V> cache;

    public GuavaCacheLayer(Duration expireAfterWrite) {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(expireAfterWrite).build();
    }

    @Override
    public Uni<V> get(K key) {
        return Uni.createFrom().item(cache.getIfPresent(key));
    }

    @Override
    public Uni<Set<V>> getAll() {
        return getMap().map(Map::values)
                       .map(Sets::newHashSet);
    }

    @Override
    public Uni<Set<V>> getAll(Predicate<V> valuePredicate) {
        return getMap().map(Map::values)
                       .onItem().transformToMulti(Multi.createFrom()::iterable)
                       .filter(valuePredicate)
                       .collectItems().with(Collectors.toUnmodifiableSet());
    }

    @Override
    public Uni<Map<K, V>> getMap() {
        return Uni.createFrom().item(cache)
                  .map(Cache::asMap);
    }

    @Override
    public Uni<V> upsert(K key, V value) {
        return Uni.createFrom().item(value)
                  .invoke(ignore -> cache.put(key, value));
    }

    @Override
    public Uni<Void> delete(K key) {
        return Uni.createFrom().item(key)
                  .invoke(cache::invalidate)
                  .flatMap(MutinyUtils::ignoreUniResult);
    }
}
