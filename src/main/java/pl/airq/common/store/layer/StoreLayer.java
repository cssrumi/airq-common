package pl.airq.common.store.layer;

import io.smallrye.mutiny.Uni;

public interface StoreLayer<K, V> {

    Uni<V> get(K key);

    Uni<V> pull(K key);

    Uni<V> upsert(K key, V value);

    Uni<Void> delete(K key);

}
