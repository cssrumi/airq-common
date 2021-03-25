package pl.airq.common.infrastructure.rest.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import pl.airq.common.domain.vo.Timestamp;

public final class TimestampSerializer extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (timestamp != null) {
            jsonGenerator.writeNumber(timestamp.value());
        } else {
            jsonGenerator.writeNull();
        }
    }
}
