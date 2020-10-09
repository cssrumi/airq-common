package pl.airq.common.store;

import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.validation.constraints.NotNull;
import pl.airq.common.store.layer.StoreLayer;

public class BasicStore<K, V> extends AbstractStore<K, V> {

    protected BasicStore(@NotNull List<StoreLayer<K, V>> storeLayers, @NotNull ExecutorService executor) {
        super(storeLayers, executor);
    }

    @Override
    protected StoreLayer<K, V> pullLayer() {
        return lastLayer();
    }
}
