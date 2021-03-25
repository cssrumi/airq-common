package pl.airq.common.infrastructure.rest.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import pl.airq.common.domain.vo.Measurement;

public final class MeasurementSerializer extends JsonSerializer<Measurement> {
    @Override
    public void serialize(Measurement measurement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(measurement.getValue());
    }
}
