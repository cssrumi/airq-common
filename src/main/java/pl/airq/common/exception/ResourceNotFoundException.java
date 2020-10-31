package pl.airq.common.exception;

import javax.ws.rs.core.Response;

public class ResourceNotFoundException extends DomainException {

    private static final long serialVersionUID = -2489718508186492931L;
    static final String RESOURCE_NOT_FOUND_TEMPLATE = "Resource not found: %s";
    public static final String DEFAULT_MESSAGE = "Resource not found.";
    public static final Response.Status STATUS = Response.Status.NOT_FOUND;

    public final Class<?> missingResourceClass;

    public ResourceNotFoundException() {
        super(DEFAULT_MESSAGE, STATUS);
        this.missingResourceClass = null;
    }

    public ResourceNotFoundException(Class<?> clazz) {
        super(String.format(RESOURCE_NOT_FOUND_TEMPLATE, clazz.getSimpleName()), STATUS);
        this.missingResourceClass = clazz;
    }

    public ResourceNotFoundException(String message) {
        super(message, STATUS);
        this.missingResourceClass = null;
    }

    public static ResourceNotFoundException fromResource(Class<?> clazz) {
        return new ResourceNotFoundException(clazz);
    }

    public static ResourceNotFoundException fromResource(String resource) {
        return new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND_TEMPLATE, resource));
    }
}
