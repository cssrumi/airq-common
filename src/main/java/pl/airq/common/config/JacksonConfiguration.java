package pl.airq.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;
import pl.airq.common.infrastructure.rest.serialization.MeasurementDeserializer;
import pl.airq.common.infrastructure.rest.serialization.MeasurementSerializer;
import pl.airq.common.infrastructure.rest.serialization.StationIdSerializer;
import pl.airq.common.infrastructure.rest.serialization.UnixTimestampDeserializer;
import pl.airq.common.vo.Measurement;
import pl.airq.common.vo.StationId;

@ApplicationScoped
public class JacksonConfiguration implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(OffsetDateTime.class, new UnixTimestampDeserializer());

        SimpleModule simpleModule = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        module.addSerializer(StationId.class, new StationIdSerializer());
        module.addSerializer(Measurement.class, new MeasurementSerializer());

        mapper.registerModules(module, simpleModule);

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
