package pl.airq.common.infrastructure.store;

import com.google.common.base.Preconditions;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.concurrent.ExecutorService;
import pl.airq.common.infrastructure.store.layer.FallbackLayer;
import pl.airq.common.infrastructure.store.layer.StoreLayer;

public class FallbackStore<V, K> extends AbstractStore<K, V> {

    protected final FallbackLayer<K, V> fallbackLayer;

    protected FallbackStore(List<StoreLayer<K, V>> layers, ExecutorService executor, FallbackLayer<K, V> fallbackLayer) {
        super(layers, executor);
        Preconditions.checkArgument(fallbackLayer != null, "Fallback layer can't be null");
        this.fallbackLayer = fallbackLayer;
    }

    @Override
    protected StoreLayer<K, V> pullLayer() {
        return fallbackLayer;
    }

    @Override
    public Uni<V> get(K key) {
        return super.get(key)
                    .onItem().ifNull().switchTo(() -> fallbackGet(key));
    }

    protected Uni<V> fallbackGet(K key) {
        return fallbackLayer.get(key)
                            .onItem().ifNotNull().call(value -> upsert(key, value));
    }
}
