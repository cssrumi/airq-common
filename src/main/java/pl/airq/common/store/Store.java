package pl.airq.common.store;

import io.smallrye.mutiny.Uni;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import pl.airq.common.store.layer.StoreLayer;

public interface Store<K, V> extends StoreLayer<K, V> {

    Uni<Set<V>> pullAll(Predicate<V> valuePredicate, Function<V, K> keyFactory);

    Uni<Map<K, V>> pullMap();

    Uni<Map<K, V>> pullMap(Predicate<K> keyPredicate);

}
