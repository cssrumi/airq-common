package pl.airq.common.infrastructure.persistance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.mutiny.sqlclient.Row;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.domain.phenotype.AirqPhenotype;
import pl.airq.common.domain.phenotype.Prediction;
import pl.airq.common.vo.StationId;

@ApplicationScoped
public class PostgresObjectFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgresObjectFactory.class);
    private final ObjectMapper mapper;

    @Inject
    PostgresObjectFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<AirqPhenotype> airqPhenotype(Row row) {
        final OffsetDateTime timestamp = row.getOffsetDateTime("timestamp");
        final String stationid = row.getString("stationid");
        final List<String> fields;
        final List<Float> values;
        final Prediction prediction;
        final Double fitness = row.getDouble("fitness");
        try {
            fields = toListOf(row.getString("fields"), String.class);
            values = toListOf(row.getString("values"), Float.class);
            prediction = mapper.readValue(row.getString("prediction"), Prediction.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("Unable to create AirqPhenotype.");
            return Optional.empty();
        }

        return Optional.of(new AirqPhenotype(timestamp, StationId.from(stationid), fields, values, prediction, fitness));
    }

    private <T> List<T> toListOf(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
