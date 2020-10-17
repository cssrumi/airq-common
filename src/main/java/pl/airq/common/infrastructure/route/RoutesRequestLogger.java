package pl.airq.common.infrastructure.route;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoutesRequestLogger implements Handler<RoutingContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoutesRequestLogger.class);

    @Override
    public void handle(RoutingContext rc) {
        LOGGER.info(String.format("Request path: %s, body: %s.", rc.normalisedPath(), rc.getBodyAsJson()));
        rc.next();
    }

    public static RoutesRequestLogger create() {
        return new RoutesRequestLogger();
    }
}
