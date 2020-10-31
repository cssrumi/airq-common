package pl.airq.common.store.layer;

import com.google.common.collect.Sets;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import pl.airq.common.process.MutinyUtils;

public class InMemoryLayer<K, V> implements StoreLayer<K, V> {

    protected final Map<K, V> cache = new ConcurrentHashMap<>();

    @Override
    public Uni<V> get(K key) {
        return Uni.createFrom().item(cache.get(key));
    }

    @Override
    public Uni<Set<V>> getAll() {
        return Uni.createFrom().item(Sets.newHashSet(cache.values()));
    }

    @Override
    public Uni<Set<V>> getAll(Predicate<V> valuePredicate) {
        return Multi.createFrom().items(cache.values()::stream)
                    .filter(valuePredicate)
                    .collectItems().with(Collectors.toUnmodifiableSet());
    }

    @Override
    public Uni<Map<K, V>> getMap() {
        return Uni.createFrom().item(Map.copyOf(cache));
    }

    @Override
    public Uni<Map<K, V>> getMap(Predicate<K> keyPredicate) {
        return Uni.createFrom().item(cache)
                  .onItem().transform(Map::entrySet).toMulti()
                  .flatMap(entries -> Multi.createFrom().iterable(entries))
                  .filter(entry -> keyPredicate.test(entry.getKey()))
                  .collectItems().asMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    @Override
    public Uni<V> upsert(K key, V value) {
        return Uni.createFrom().item(cache.put(key, value));
    }

    @Override
    public Uni<Void> delete(K key) {
        return Uni.createFrom().item(cache.remove(key))
                  .onItem().transformToUni(MutinyUtils::ignoreUniResult);
    }
}
