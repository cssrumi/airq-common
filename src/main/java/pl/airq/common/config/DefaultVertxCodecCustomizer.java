package pl.airq.common.config;

import io.quarkus.runtime.Startup;
import io.vertx.core.json.jackson.DatabindCodec;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class DefaultVertxCodecCustomizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultVertxCodecCustomizer.class);

    private final DefaultJacksonCustomizer jacksonCustomizer;

    @Inject
    public DefaultVertxCodecCustomizer(DefaultJacksonCustomizer jacksonCustomizer) {
        this.jacksonCustomizer = jacksonCustomizer;
    }

    @PostConstruct
    void customize() {
        jacksonCustomizer.customize(DatabindCodec.mapper());
        jacksonCustomizer.customize(DatabindCodec.prettyMapper());
        LOGGER.info("Vertx mappers customized.");
    }
}
