package pl.airq.common.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import pl.airq.common.vo.Measurement;

public class MeasurementSerializer extends JsonSerializer<Measurement> {
    @Override
    public void serialize(Measurement measurement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(measurement.getValue());
    }
}
