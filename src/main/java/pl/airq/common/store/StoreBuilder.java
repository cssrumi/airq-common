package pl.airq.common.store;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import pl.airq.common.store.layer.FallbackLayer;
import pl.airq.common.store.layer.InMemoryLayer;
import pl.airq.common.store.layer.StoreLayer;


public class StoreBuilder<K, V> {

    private static final int DEFAULT_EXECUTOR_THREADS = 2;

    private boolean addInMemoryLayer = false;
    private List<StoreLayer<K, V>> layers = new ArrayList<>();
    private FallbackLayer<K, V> fallbackLayer = null;
    private ExecutorService executor = null;

    public StoreBuilder<K, V> withFallback(FallbackLayer<K, V> fallbackLayer) {
        this.fallbackLayer = fallbackLayer;
        return this;
    }

    public StoreBuilder<K, V> withInMemoryAtTop() {
        addInMemoryLayer = true;
        return this;
    }

    public StoreBuilder<K, V> withLayer(StoreLayer<K, V> layer) {
        layers.add(layer);
        return this;
    }

    public StoreBuilder<K, V> withExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public Store<K, V> build() {
        if (addInMemoryLayer) {
            layers.add(0, new InMemoryLayer<>());
        }

        if (executor == null) {
            executor = Executors.newFixedThreadPool(DEFAULT_EXECUTOR_THREADS);
        }

        if (fallbackLayer == null) {
            return new BasicStore<>(List.copyOf(layers), executor);
        }
        return new FallbackStore<>(List.copyOf(layers), executor, fallbackLayer);
    }
}
