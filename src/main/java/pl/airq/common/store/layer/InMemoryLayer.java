package pl.airq.common.store.layer;

import io.smallrye.mutiny.Uni;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import pl.airq.common.process.MutinyUtils;

public class InMemoryLayer<K, V> implements StoreLayer<K, V> {

    protected final Map<K, V> cache = new ConcurrentHashMap<>();

    @Override
    public Uni<V> get(K key) {
        return Uni.createFrom().item(cache.get(key));
    }

    @Override
    public Uni<V> pull(K key) {
        return get(key);
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
