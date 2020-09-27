package pl.airq.common.config;

import pl.airq.common.process.EventParser;

public interface EventParserCustomizer {

    void customize(EventParser parser);

}
