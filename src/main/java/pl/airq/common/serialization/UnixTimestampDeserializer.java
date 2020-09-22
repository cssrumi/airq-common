package pl.airq.common.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.commons.lang3.StringUtils;

public class UnixTimestampDeserializer extends JsonDeserializer<OffsetDateTime> {
    private final Logger LOGGER = LoggerFactory.getLogger(UnixTimestampDeserializer.class);

    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final JsonToken token = jp.getCurrentToken();

        switch (token) {
            case VALUE_NUMBER_FLOAT:
                return OffsetDateTime.ofInstant(Instant.ofEpochSecond(Double.valueOf(jp.getDoubleValue()).longValue()), ZoneOffset.UTC);
            case VALUE_NUMBER_INT:
                return OffsetDateTime.ofInstant(Instant.ofEpochSecond(jp.getIntValue()), ZoneOffset.UTC);
            case VALUE_NULL:
                return null;
            case VALUE_STRING:
                final String timestamp = jp.getText().trim();
                if (StringUtils.isNumeric(timestamp)) {
                    return OffsetDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(timestamp)), ZoneOffset.UTC);
                }
            default:
                LOGGER.warn("Unable to deserialize timestamp: {}", jp.getText());
                return null;
        }

    }
}
