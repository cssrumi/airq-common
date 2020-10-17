package pl.airq.common.store;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.airq.common.store.layer.GuavaCacheLayer;
import pl.airq.common.store.layer.InMemoryLayer;

import static org.junit.jupiter.api.Assertions.assertNull;

class BasicStoreTest {

    private static final Duration MAX_AWAIT_TIME = Duration.ofSeconds(10);
    private BasicStore<String, String> store;

    @BeforeEach
    void beforeEach() {
        store = new BasicStore<>(
                List.of(new GuavaCacheLayer<>(MAX_AWAIT_TIME), new InMemoryLayer<>()),
                Executors.newSingleThreadExecutor());
    }

    @Test
    void get() {
        final String result = store.get("NOT_EXISTING_KEY").await().atMost(MAX_AWAIT_TIME);

        assertNull(result);
    }
}
