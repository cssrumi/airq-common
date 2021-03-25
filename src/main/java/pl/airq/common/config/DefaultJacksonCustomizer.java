package pl.airq.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import javax.enterprise.context.ApplicationScoped;
import pl.airq.common.infrastructure.rest.serialization.MeasurementDeserializer;
import pl.airq.common.infrastructure.rest.serialization.MeasurementSerializer;
import pl.airq.common.infrastructure.rest.serialization.StationIdSerializer;
import pl.airq.common.infrastructure.rest.serialization.TimestampDeserializer;
import pl.airq.common.domain.vo.Measurement;
import pl.airq.common.domain.vo.StationId;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.infrastructure.rest.serialization.TimestampSerializer;

@ApplicationScoped
public class DefaultJacksonCustomizer implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        JavaTimeModule module = new JavaTimeModule();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Timestamp.class, new TimestampSerializer());
        simpleModule.addDeserializer(Timestamp.class, new TimestampDeserializer());
        simpleModule.addSerializer(Measurement.class, new MeasurementSerializer());
        simpleModule.addDeserializer(Measurement.class, new MeasurementDeserializer());
        simpleModule.addSerializer(StationId.class, new StationIdSerializer());
        mapper.registerModules(module, simpleModule);

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
