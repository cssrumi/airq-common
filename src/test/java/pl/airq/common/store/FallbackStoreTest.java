package pl.airq.common.store;

import io.smallrye.mutiny.Uni;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import pl.airq.common.store.layer.FallbackLayer;
import pl.airq.common.store.layer.StoreLayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class FallbackStoreTest {

    @Mock
    private StoreLayer<String, String> layer;
    @Mock
    private FallbackLayer<String, String> fallback;

    private FallbackStore<String, String> store;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void beforeEach() {
        initMocks(this);
        var layers = List.of(layer);
        var executor = Executors.newSingleThreadExecutor();
        store = new FallbackStore<>(layers, executor, fallback);
    }

    @Test
    void get_withNullInLayer_andValueInFallback_expectValueFromFallback() {
        var key = "key";
        var value = "value";

        when(layer.get(eq(key))).thenReturn(Uni.createFrom().nullItem());
        when(fallback.get(eq(key))).thenReturn(Uni.createFrom().item(value));
        when(layer.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));
        when(fallback.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));

        String result = store.get(key).await().atMost(Duration.ofSeconds(1));

        assertThat(result).isEqualTo(value);
        verify(layer, times(1)).get(eq(key));
        verify(fallback, times(1)).get(eq(key));
        verify(layer, times(1)).upsert(eq(key), eq(value));
        verify(fallback, times(0)).upsert(eq(key), eq(value));
    }

    @Test
    void get_withValueInLayer_andNullInFallback_expectValueFromLayer() {
        var key = "key";
        var value = "value";

        when(layer.get(eq(key))).thenReturn(Uni.createFrom().item(value));
        when(fallback.get(eq(key))).thenReturn(Uni.createFrom().nullItem());
        when(layer.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));
        when(fallback.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));

        String result = store.get(key).await().atMost(Duration.ofSeconds(1));

        assertThat(result).isEqualTo(value);
        verify(layer, times(1)).get(eq(key));
        verify(fallback, times(0)).get(eq(key));
        verify(layer, times(1)).upsert(eq(key), eq(value));
        verify(fallback, times(0)).upsert(eq(key), eq(value));
    }

    @Test
    void get_withNullInLayer_andNullInFallback_expectNull() {
        var key = "key";

        when(layer.get(eq(key))).thenReturn(Uni.createFrom().nullItem());
        when(fallback.get(eq(key))).thenReturn(Uni.createFrom().nullItem());
        when(layer.upsert(eq(key), isNull())).thenReturn(Uni.createFrom().nullItem());
        when(fallback.upsert(eq(key), isNull())).thenReturn(Uni.createFrom().nullItem());

        String result = store.get(key).await().atMost(Duration.ofSeconds(1));

        assertThat(result).isNull();
        verify(layer, times(1)).get(eq(key));
        verify(fallback, times(1)).get(eq(key));
        verify(layer, times(0)).upsert(eq(key), isNull());
        verify(fallback, times(0)).upsert(eq(key), isNull());
    }
}
