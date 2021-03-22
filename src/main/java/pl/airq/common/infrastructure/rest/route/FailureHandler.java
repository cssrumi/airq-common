package pl.airq.common.infrastructure.rest.route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;
import java.util.Optional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.exception.DomainException;

public class FailureHandler implements Handler<RoutingContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureHandler.class);

    @Override
    public void handle(RoutingContext event) {
        Throwable failure = event.failure();
        if (failure instanceof DomainException) {
            DomainException domainException = (DomainException) failure;
            int status = Optional.ofNullable(domainException.getStatus()).orElse(Response.Status.INTERNAL_SERVER_ERROR).getStatusCode();
            String message = failure.getMessage();

            LOGGER.warn("Failure resolved with status: {}, message: {}", status, message);
            event.response()
                 .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                 .setStatusCode(status).end(message);
            return;
        }

        event.next();
    }
}
