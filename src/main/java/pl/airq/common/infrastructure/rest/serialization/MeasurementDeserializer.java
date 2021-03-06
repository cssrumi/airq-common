package pl.airq.common.infrastructure.rest.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.IOException;
import pl.airq.common.domain.vo.Measurement;

public final class MeasurementDeserializer extends JsonDeserializer<Measurement> {

    private final Logger LOGGER = LoggerFactory.getLogger(MeasurementDeserializer.class);

    @Override
    public Measurement deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
        final JsonToken token = jp.getCurrentToken();

        switch (token) {
            case VALUE_NUMBER_FLOAT:
                return Measurement.from(jp.getFloatValue());
            case VALUE_NUMBER_INT:
                return Measurement.fromInteger(jp.getIntValue());
            case VALUE_STRING:
                return Measurement.fromString(jp.getText());
            case VALUE_NULL:
                return Measurement.EMPTY;
            default:
                LOGGER.warn("Unable to parse Measurement: {}", jp.getText());
                return Measurement.EMPTY;
        }
    }
}
