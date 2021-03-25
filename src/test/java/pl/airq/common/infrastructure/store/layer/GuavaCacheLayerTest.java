package pl.airq.common.infrastructure.store.layer;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuavaCacheLayerTest {

    private static final Duration MAX_AWAIT_TIME = Duration.ofSeconds(10);
    private GuavaCacheLayer<String, String> layer;

    @BeforeEach
    void beforeEach() {
        layer = new GuavaCacheLayer<>(MAX_AWAIT_TIME);
    }

    @Test
    void get_withNotExistingKey_expectNull() {
        final String result = layer.get("NOT_EXISTING_KEY").await().atMost(MAX_AWAIT_TIME);

        assertNull(result);
    }

    @Test
    void getAll() {
    }

    @Test
    void testGetAll() {
    }

    @Test
    void getMap() {
    }

    @Test
    void upsert() {
    }

    @Test
    void delete() {
    }
}
