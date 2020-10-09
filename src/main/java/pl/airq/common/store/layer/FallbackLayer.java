package pl.airq.common.store.layer;

import io.smallrye.mutiny.Uni;

public abstract class FallbackLayer<K, V> implements StoreLayer<K, V>{

    @Override
    public Uni<V> pull(K key) {
        return get(key);
    }

    @Override
    public Uni<V> upsert(K key, V value) {
        return Uni.createFrom().item(value);
    }

    @Override
    public Uni<Void> delete(K key) {
        return Uni.createFrom().voidItem();
    }
}
