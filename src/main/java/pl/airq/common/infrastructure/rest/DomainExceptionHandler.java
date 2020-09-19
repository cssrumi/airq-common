package pl.airq.common.infrastructure.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import pl.airq.common.domain.exception.DomainException;

@Provider
public class DomainExceptionHandler implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException e) {
        return Response.fromResponse(e.getResponse())
                       .entity(e.getMessage())
                       .build();
    }
}
