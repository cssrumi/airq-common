package pl.airq.common.infrastructure.rest.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import pl.airq.common.domain.vo.Timestamp;

public final class TimestampDeserializer extends JsonDeserializer<Timestamp> {
    private final Logger LOGGER = LoggerFactory.getLogger(TimestampDeserializer.class);

    @Override
    public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        final JsonToken token = jp.getCurrentToken();

        switch (token) {
            case VALUE_NUMBER_FLOAT:
                return Timestamp.create(Double.valueOf(jp.getDoubleValue()).longValue());
            case VALUE_NUMBER_INT:
                return Timestamp.create(jp.getLongValue());
            case VALUE_NULL:
                return null;
            case VALUE_STRING:
                final String timestamp = jp.getText().trim();
                if (StringUtils.isNumeric(timestamp)) {
                    return Timestamp.create(Long.parseLong(timestamp));
                }
            default:
                LOGGER.warn("Unable to deserialize timestamp: {}", jp.getText());
                return null;
        }

    }
}
