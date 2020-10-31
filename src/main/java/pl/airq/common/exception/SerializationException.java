package pl.airq.common.exception;

import javax.ws.rs.core.Response;

public class SerializationException extends DomainException {

    private static final long serialVersionUID = -2922863246624460871L;
    static final String SERIALIZATION_MESSAGE_TEMPLATE = "Unable to serialize object: %s";
    public static final String DEFAULT_MESSAGE = "Unable to serialize object.";
    public static final Response.Status DEFAULT_STATUS = Response.Status.BAD_REQUEST;

    public SerializationException(String message) {
        super(message, DEFAULT_STATUS);
    }

    public SerializationException(Object object, Throwable throwable) {
        super(String.format(SERIALIZATION_MESSAGE_TEMPLATE, object), DEFAULT_STATUS, throwable);
    }

    public SerializationException(Throwable throwable) {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS,throwable);
    }

    public SerializationException(String message, Response.Status status, Throwable throwable) {
        super(message, status, throwable);
    }
}
