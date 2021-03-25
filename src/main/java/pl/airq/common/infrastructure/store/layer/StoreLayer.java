package pl.airq.common.infrastructure.store.layer;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public interface StoreLayer<K, V> {

    Uni<V> get(K key);

    Uni<Set<V>> getAll();

    Uni<Set<V>> getAll(Predicate<V> valuePredicate);

    Uni<Map<K, V>> getMap();

    default Uni<Map<K, V>> getMap(Predicate<K> keyPredicate) {
        return getMap().onItem().transform(Map::entrySet).toMulti()
                       .flatMap(entries -> Multi.createFrom().iterable(entries))
                       .filter(entry -> keyPredicate.test(entry.getKey()))
                       .collectItems().asMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    default Uni<V> pull(K key) {
        return get(key);
    }

    Uni<V> upsert(K key, V value);

    Uni<Void> delete(K key);

}
