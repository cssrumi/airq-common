package pl.airq.common.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class UnixTimestampDeserializer extends JsonDeserializer<OffsetDateTime> {
    private final Logger log = LoggerFactory.getLogger(UnixTimestampDeserializer.class);

    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String timestamp = jp.getText().trim();
        try {
            return OffsetDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(timestamp)), ZoneOffset.UTC);
        } catch (NumberFormatException e) {
            log.warn("Unable to deserialize timestamp: " + timestamp, e);
            return null;
        }
    }
}
