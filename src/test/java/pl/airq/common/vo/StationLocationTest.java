package pl.airq.common.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationLocationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void JsonCreator_from_withStringValue_expectValidObject() throws JsonProcessingException {
        Float lon = 1234.0f;
        Float lat = 2345.0f;
        final String raw = TestVoClass.getRaw("\"" + lon.toString() + "," + lat.toString() + "\"");

        final TestVoClass<StationLocation> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<StationLocation>>() {});

        assertNotNull(result);
        assertNotNull(result.field);
        assertNotNull(result.field.getLon());
        assertNotNull(result.field.getLat());
        assertTrue(result.field.getLon() > 0);
        assertTrue(result.field.getLat() > 0);
        assertTrue(Math.abs(result.field.getLon() - lon) < 0.01);
        assertTrue(Math.abs(result.field.getLat() - lat) < 0.01);
    }

    @Test
    void JsonCreator_from_withStringValue_andNECoords_expectValidObject() throws JsonProcessingException {
        Float lon = 1234.0f;
        Float lat = 2345.0f;
        final String raw = TestVoClass.getRaw("\"" + lon.toString() + ",N," + lat.toString() + ",E\"");

        final TestVoClass<StationLocation> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<StationLocation>>() {});

        assertNotNull(result);
        assertNotNull(result.field);
        assertNotNull(result.field.getLon());
        assertNotNull(result.field.getLat());
        assertTrue(result.field.getLon() > 0);
        assertTrue(result.field.getLat() > 0);
        assertTrue(Math.abs(result.field.getLon() - lon) < 0.01);
        assertTrue(Math.abs(result.field.getLat() - lat) < 0.01);
    }

    @Test
    void JsonCreator_from_withStringValue_andSWCoords_and0prefix_expectValidObject() throws JsonProcessingException {
        Float lon = 1234.0f;
        Float lat = 2345.0f;
        final String raw = TestVoClass.getRaw("\"0" + lon.toString() + ",S,0" + lat.toString() + ",W\"");

        final TestVoClass<StationLocation> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<StationLocation>>() {});

        assertNotNull(result);
        assertNotNull(result.field);
        assertNotNull(result.field.getLon());
        assertNotNull(result.field.getLat());
        assertTrue(result.field.getLon() < 0);
        assertTrue(result.field.getLat() < 0);
        assertTrue(Math.abs(Math.abs(result.field.getLon()) - lon) < 0.1);
        assertTrue(Math.abs(Math.abs(result.field.getLat()) - lat) < 0.1);
    }

    @Test
    void JsonCreator_from_withEmptyStringValue_expectEmptyObject() throws JsonProcessingException {
        final String raw = TestVoClass.getRaw("\"\"");

        final TestVoClass<StationLocation> result = objectMapper.readValue(raw, new TypeReference<TestVoClass<StationLocation>>() {});

        assertNotNull(result);
        assertNotNull(result.field);
        assertNull(result.field.getLon());
        assertNull(result.field.getLat());
    }
}
