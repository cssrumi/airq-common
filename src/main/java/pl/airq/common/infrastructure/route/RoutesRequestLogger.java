package pl.airq.common.infrastructure.route;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoutesRequestLogger implements Handler<RoutingContext> {

    private static final String EMPTY = "EMPTY";
    private static final Logger LOGGER = LoggerFactory.getLogger(RoutesRequestLogger.class);

    @Override
    public void handle(RoutingContext rc) {
        String path = rc.normalisedPath();
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }

        String body = rc.getBodyAsString();
        if (StringUtils.isEmpty(body)) {
            body = EMPTY;
        }

        LOGGER.info("Request path: {}, body: {}.", path, body);
        rc.next();
    }

    public static RoutesRequestLogger create() {
        return new RoutesRequestLogger();
    }
}
