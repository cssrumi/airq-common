package pl.airq.common.infrastructure.store;

import com.google.common.collect.Lists;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.groups.MultiFlatten;
import java.util.List;
import pl.airq.common.infrastructure.store.layer.StoreLayer;

public enum PropagationOrder {
    DISORDERED, TO_LOWER, TO_UPPER;

    <K, V> List<StoreLayer<K, V>> prepare(List<StoreLayer<K, V>> layers) {
        return this == TO_LOWER ? layers : Lists.reverse(layers);
    }

    <K, V, R> Multi<R> flat(MultiFlatten<StoreLayer<K, V>, R> flatten) {
        return this == DISORDERED ? flatten.merge() : flatten.concatenate();
    }
}
