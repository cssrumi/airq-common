package pl.airq.common.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.NativeImageTest;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import pl.airq.common.config.JacksonConfiguration;
import pl.airq.common.domain.measurement.AirqMeasurementEvent;
import pl.airq.common.domain.measurement.AirqMeasurementPayload;
import pl.airq.common.process.event.AirqEvent;
import pl.airq.common.vo.Measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.airq.common.process.EventParserTest.RAW_AIRQ_MEASUREMENT_EVENT;

@NativeImageTest
public class NativeEventParserIT {

    static ObjectMapper objectMapper;
    static EventParser parser;

    static {
        objectMapper = new ObjectMapper();
        new JacksonConfiguration().customize(objectMapper);
        parser = new EventParser(objectMapper);
    }

    @Test
    void deserializeDomainEvent_fromSerializedEvent_expectValidInstance() {
        final AirqMeasurementPayload payload = new AirqMeasurementPayload();
        payload.humidity = Measurement.fromInteger(123);
        payload.temperature = Measurement.fromString("4321");
        final AirqMeasurementEvent event = new AirqMeasurementEvent(OffsetDateTime.now(), payload);
        final String serializedEvent = parser.parse(event);

        final AirqEvent<?> airqEvent = parser.deserializeDomainEvent(serializedEvent);

        assertTrue(airqEvent instanceof AirqMeasurementEvent);
        assertEquals(AirqMeasurementEvent.class.getSimpleName(), airqEvent.eventType());
    }

    @Test
    void deserializeDomainEvent_fromRawEvent_expectValidInstance() {
        final AirqEvent airqEvent = parser.deserializeDomainEvent(RAW_AIRQ_MEASUREMENT_EVENT);

        assertTrue(airqEvent instanceof AirqMeasurementEvent);
        assertEquals(AirqMeasurementEvent.class.getSimpleName(), airqEvent.eventType());
    }
}
