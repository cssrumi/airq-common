package pl.airq.common.infrastructure.store;

import io.smallrye.mutiny.Uni;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import pl.airq.common.infrastructure.store.layer.StoreLayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class AbstractStoreTest {

    @Mock
    private StoreLayer<String, String> layer1;
    @Mock
    private StoreLayer<String, String> layer2;

    private AbstractStore<String, String> store;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void beforeEach() {
        initMocks(this);
        var layers = List.of(layer1, layer2);
        var executor = Executors.newSingleThreadExecutor();
        store = mock(AbstractStore.class, withSettings().defaultAnswer(Answers.CALLS_REAL_METHODS).useConstructor(layers, executor));
    }

    @Test
    void get_withNullIn1stLayer_andValueIn2ndLayer_expectValueFrom2ndLayer() {
        var key = "key";
        var value = "value";

        when(layer1.get(eq(key))).thenReturn(Uni.createFrom().nullItem());
        when(layer2.get(eq(key))).thenReturn(Uni.createFrom().item(value));
        when(layer1.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));
        when(layer2.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));

        String result = store.get(key).await().atMost(Duration.ofSeconds(1));

        assertThat(result).isEqualTo(value);
        verify(layer1, times(1)).get(eq(key));
        verify(layer2, times(1)).get(eq(key));
        verify(layer1, times(1)).upsert(eq(key), eq(value));
        verify(layer2, times(1)).upsert(eq(key), eq(value));
    }

    @Test
    void get_withValueIn1stLayer_andNullIn2ndLayer_expectValueFrom1stLayer() {
        var key = "key";
        var value = "value";

        when(layer1.get(eq(key))).thenReturn(Uni.createFrom().item(value));
        when(layer2.get(eq(key))).thenReturn(Uni.createFrom().nullItem());
        when(layer1.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));
        when(layer2.upsert(eq(key), eq(value))).thenReturn(Uni.createFrom().item(value));

        String result = store.get(key).await().atMost(Duration.ofSeconds(1));

        assertThat(result).isEqualTo(value);
        verify(layer1, times(1)).get(eq(key));
        verify(layer2, times(0)).get(eq(key));
        verify(layer1, times(1)).upsert(eq(key), eq(value));
        verify(layer2, times(1)).upsert(eq(key), eq(value));
    }

}
