package pl.airq.common.process;

import io.quarkus.test.junit.QuarkusTest;
import java.time.OffsetDateTime;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.ctx.internal.AirqMeasurementEvent;
import pl.airq.common.process.ctx.internal.AirqMeasurementPayload;
import pl.airq.common.process.event.AirqEvent;
import pl.airq.common.domain.vo.Measurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class EventParserTest {

    static final String RAW_AIRQ_MEASUREMENT_EVENT = "{\"eventType\": \"AirqMeasurement\",\"timestamp\": 1599854402,\"payload\": " +
            "{\"Temperature\": \"23.70°C\"," +
            "\"Humidity\": \"66%\"," +
            "\"PM10\": 518," +
            "\"PM25\": 376," +
            "\"stationId\": \"00000000164ab68a\"," +
            "\"Location\": \"5424.831306,N,01826.459895,E\"}}";

    @Inject
    EventParser parser;

    @Test
    void deserializeDomainEvent_fromSerializedEvent_expectValidInstance() {
        final AirqMeasurementPayload payload = new AirqMeasurementPayload();
        payload.humidity = Measurement.fromInteger(123);
        payload.temperature = Measurement.fromString("4321");
        final AirqMeasurementEvent event = new AirqMeasurementEvent(Timestamp.now(), payload);
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
