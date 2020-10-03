package pl.airq.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.DefaultBean;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.process.EventParser;

import static java.util.stream.Collectors.toList;

@Dependent
class EventParserProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventParserProvider.class);

    @Produces
    @Singleton
    @DefaultBean
    EventParser eventParser(Instance<EventParserCustomizer> configures, ObjectMapper mapper) {
        EventParser eventParser = new EventParser(mapper);
        for (EventParserCustomizer configure : configures) {
            configure.customize(eventParser);
        }
        LOGGER.info("{} has been configured for: {}",
                EventParser.class.getSimpleName(),
                eventParser.registeredEvents().stream().map(Class::getSimpleName).collect(toList()));

        return eventParser;
    }

}
