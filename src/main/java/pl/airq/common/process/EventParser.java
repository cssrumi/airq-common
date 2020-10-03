package pl.airq.common.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.domain.enriched.AirqDataEnrichedEvent;
import pl.airq.common.domain.exception.DeserializationException;
import pl.airq.common.domain.exception.ProcessingException;
import pl.airq.common.domain.exception.SerializationException;
import pl.airq.common.domain.measurement.AirqMeasurementEvent;
import pl.airq.common.domain.phenotype.AirqPhenotypeCreatedEvent;
import pl.airq.common.process.event.AirqEvent;
import pl.airq.common.process.event.Event;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class EventParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventParser.class);
    private static final String EVENT_TYPE_FIELD = "eventType";
    public static final Set<Class<? extends AirqEvent>> DEFAULT_DOMAIN_EVENTS = Set
            .of(AirqMeasurementEvent.class, AirqDataEnrichedEvent.class, AirqPhenotypeCreatedEvent.class);
    private final ObjectMapper mapper;
    private final Map<String, Class<? extends AirqEvent>> domainEventsMap = new HashMap<>();

    @Inject
    public EventParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public EventParser registerEvents(Set<Class<? extends AirqEvent>> events) {
        domainEventsMap.putAll(createMap(events));
        return this;
    }

    public Try<String> tryParse(Event event) {
        return Try.of(() -> parse(event));
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

    public <T> Try<T> tryParse(String rawEvent, Class<T> eventClass) {
        return Try.of(() -> parse(rawEvent, eventClass));
    }

    public <T> T parse(String rawEvent, Class<T> eventClass) {
        try {
            return mapper.readValue(rawEvent, eventClass);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error occurred while deserializing {} class from: {}", eventClass.getSimpleName(), rawEvent);
            throw new DeserializationException(e);
        }
    }

    public Try<AirqEvent> tryDeserializeDomainEvent(String rawEvent) {
        return Try.of(() -> deserializeDomainEvent(rawEvent));
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
            final Class<?> clsType = domainEventsMap.get(eventType);
            final Object object = mapper.treeToValue(jsonNode, clsType);
            return (AirqEvent) object;
        } catch (JsonProcessingException | ProcessingException e) {
            LOGGER.error("Unable to deserialize domain event: {}", rawEvent, e);
            throw new DeserializationException(rawEvent, e);
        }
    }

    public Set<Class<? extends AirqEvent>> registeredEvents() {
        return new HashSet<>(this.domainEventsMap.values());
    }

    @Deprecated
    public static Set<Class<? extends AirqEvent>> findDomainEventsUsingReflection() {
        return findDomainEventsUsingReflection("pl.airq.common.domain");
    }

    @Deprecated
    public static Set<Class<? extends AirqEvent>> findDomainEventsUsingReflection(String path) {
        Reflections reflections = new Reflections(path);
        final Set<Class<? extends AirqEvent>> classes = Optional.ofNullable(reflections.getSubTypesOf(AirqEvent.class))
                                                                .orElse(Collections.emptySet());
        LOGGER.info("Domain events found using reflection: {}", classes.stream().map(Class::getSimpleName).collect(toList()));
        return classes;
    }

    private static Map<String, Class<? extends AirqEvent>> createMap(Set<Class<? extends AirqEvent>> events) {
        final Map<String, Class<? extends AirqEvent>> eventMap = events
                .stream()
                .collect(toMap(Class::getSimpleName, (clazz) -> clazz));
        final Map<String, Class<? extends AirqEvent>> shorterEventMap = eventMap
                .entrySet()
                .stream()
                .collect(toMap(entry -> entry.getKey().replace("Event", ""), Map.Entry::getValue));
        eventMap.putAll(shorterEventMap);
        return eventMap;
    }
}
