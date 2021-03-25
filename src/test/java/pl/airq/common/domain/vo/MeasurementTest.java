package pl.airq.common.domain.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void JsonCreator_fromInteger_expectValidValue() throws JsonProcessingException {
        Integer expectedValue = 123;
        String raw = TestVoClass.getRaw(expectedValue.toString());

        final TestVoClass<Measurement> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<Measurement>>(){});

        assertNotNull(result);
        assertNotNull(result.field);
        assertEquals(expectedValue, result.field.getValue().intValue());
    }

    @Test
    void JsonCreator_fromDouble_expectValidValue() throws JsonProcessingException {
        Double expectedValue = 123.45;
        String raw = TestVoClass.getRaw(expectedValue.toString());

        final TestVoClass<Measurement> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<Measurement>>(){});

        assertNotNull(result);
        assertNotNull(result.field);
        assertTrue(Math.abs(expectedValue - result.field.getDoubleValue()) < 0.02);
    }

    @Test
    void JsonCreator_fromString_withC_expectValidValue() throws JsonProcessingException {
        String raw = TestVoClass.getRaw("\"123°C\"");

        final TestVoClass<Measurement> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<Measurement>>(){});

        assertNotNull(result);
        assertNotNull(result.field);
        assertEquals(123, result.field.getValue().intValue());
    }

    @Test
    void JsonCreator_fromString_withPercent_expectValidValue() throws JsonProcessingException {
        String raw = TestVoClass.getRaw("\"123%\"");

        final TestVoClass<Measurement> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<Measurement>>(){});

        assertNotNull(result);
        assertNotNull(result.field);
        assertEquals(123, result.field.getValue().intValue());
    }

    @Test
    void JsonCreator_fromEmptyString_expectEmptyObject() throws JsonProcessingException {
        String raw = TestVoClass.getRaw("\"\"");

        final TestVoClass<Measurement> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<Measurement>>(){});

        assertNotNull(result);
        assertNotNull(result.field);
        assertNull(result.field.getValue());
        assertNull(result.field.getDoubleValue());
    }
}
