package pl.airq.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import java.time.OffsetDateTime;
import javax.inject.Singleton;
import pl.airq.common.vo.Measurement;

@Singleton
public class JacksonConfiguration implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(OffsetDateTime.class, new UnixTimestampDeserializer());

        SimpleModule simpleModule = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());

        mapper.registerModules(module, simpleModule);
    }
}
