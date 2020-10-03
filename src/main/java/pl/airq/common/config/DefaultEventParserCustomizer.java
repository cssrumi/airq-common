package pl.airq.common.config;

import javax.enterprise.context.Dependent;
import pl.airq.common.process.EventParser;

@Dependent
class DefaultEventParserCustomizer implements EventParserCustomizer {

    @Override
    public void customize(EventParser parser) {
        parser.registerEvents(EventParser.DEFAULT_DOMAIN_EVENTS);
    }
}
