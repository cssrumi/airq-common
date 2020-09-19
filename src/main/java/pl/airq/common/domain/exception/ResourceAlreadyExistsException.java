package pl.airq.common.domain.exception;

import javax.ws.rs.core.Response;

public class ResourceAlreadyExistsException extends DomainException {

    private static final long serialVersionUID = 3472527748128655029L;
    static final String RESOURCE_ALREADY_EXISTS_TEMPLATE = "Resource already exists: %s";
    public static final String DEFAULT_MESSAGE = "Resource already exists.";
    public static final Response.Status STATUS = Response.Status.CONFLICT;

    public ResourceAlreadyExistsException() {
        super(DEFAULT_MESSAGE, STATUS);
    }

    public ResourceAlreadyExistsException(String message) {
        super(message, STATUS);
    }

    public static ResourceAlreadyExistsException fromResource(Class clazz) {
        return new ResourceAlreadyExistsException(String.format(RESOURCE_ALREADY_EXISTS_TEMPLATE, clazz.getSimpleName()));
    }

    public static ResourceAlreadyExistsException fromResource(String resource) {
        return new ResourceAlreadyExistsException(String.format(RESOURCE_ALREADY_EXISTS_TEMPLATE, resource));
    }
}
