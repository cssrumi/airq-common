package pl.airq.common.infrastructure.rest.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import pl.airq.common.domain.vo.StationId;

public final class StationIdSerializer extends JsonSerializer<StationId> {

    @Override
    public void serialize(StationId stationId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(stationId.value());
    }
}
