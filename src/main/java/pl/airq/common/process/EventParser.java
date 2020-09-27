package pl.airq.common.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.domain.exception.DeserializationException;
import pl.airq.common.domain.exception.ProcessingException;
import pl.airq.common.domain.exception.SerializationException;
import pl.airq.common.process.event.AirqEvent;
import pl.airq.common.process.event.Event;

@ApplicationScoped
public class EventParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventParser.class);
    private static final String EVENT_TYPE_FIELD = "eventType";
    private final ObjectMapper mapper;
    private final Map<String, Class<? extends AirqEvent>> domainEvents;

    @Inject
    public EventParser(ObjectMapper mapper) {
        this.mapper = mapper;
        this.domainEvents = findDomainEvents();
    }

    public String parse(Event event) {
        try {
            final String rawEvent = mapper.writeValueAsString(event);
            LOGGER.info(String.format("RawEvent: %s", rawEvent));
            return rawEvent;
        } catch (JsonProcessingException e) {
            LOGGER.error("Error occurred mapping {}: {}", event.getClass().getSimpleName(), event.toString(), e);
            throw new SerializationException(event, e);
        }
    }

    public <T> T parse(String rawEvent, Class<T> eventClass) {
        try {
            return mapper.readValue(rawEvent, eventClass);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error occurred while deserializing {} class from: {}", eventClass.getSimpleName(), rawEvent);
            throw new DeserializationException(e);
        }
    }

    public AirqEvent deserializeDomainEvent(String rawEvent) {
        if (StringUtils.isEmpty(rawEvent)) {
            throw new DeserializationException("Event was empty");
        }

        try {
            JsonNode jsonNode = mapper.readTree(rawEvent);
            final String eventType = Optional.ofNullable(jsonNode)
                                             .map(node -> node.get(EVENT_TYPE_FIELD))
                                             .map(JsonNode::textValue)
                                             .orElseThrow(() -> DeserializationException.missingField(EVENT_TYPE_FIELD));
            final Class<?> clsType = domainEvents.get(eventType);
            final Object object = mapper.treeToValue(jsonNode, clsType);
            return (AirqEvent) object;
        } catch (JsonProcessingException | ProcessingException e) {
            LOGGER.error("Unable to deserialize domain event: {}", rawEvent, e);
            throw new DeserializationException(rawEvent, e);
        }
    }

    private static Map<String, Class<? extends AirqEvent>> findDomainEvents() {
        Reflections reflections = new Reflections("pl.airq.common.domain");
        final Map<String, Class<? extends AirqEvent>> events = reflections.getSubTypesOf(AirqEvent.class)
                                                                          .stream()
                                                                          .collect(Collectors
                                                                                  .toMap(Class::getSimpleName, (clazz) -> clazz));
        final Map<String, Class<? extends AirqEvent>> shorterEvents = events.entrySet()
                                                                            .stream()
                                                                            .collect(Collectors.toMap(
                                                                                    entry -> entry.getKey().replace("Event", ""),
                                                                                    Map.Entry::getValue));
        LOGGER.info("Found domain events: {}", events.keySet());
        events.putAll(shorterEvents);
        return Map.copyOf(events);
    }
}
