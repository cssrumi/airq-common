package pl.airq.common.infrastructure.rest.jaxrs;

import io.vertx.core.eventbus.ReplyException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ReplyExceptionHandler implements ExceptionMapper<ReplyException> {

    @Override
    public Response toResponse(ReplyException e) {
        return Response.status(500)
                       .entity(e.getMessage())
                       .build();
    }
}
