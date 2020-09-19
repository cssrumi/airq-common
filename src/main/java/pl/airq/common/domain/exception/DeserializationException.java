package pl.airq.common.domain.exception;

import javax.ws.rs.core.Response;

public class DeserializationException extends DomainException {

    private static final long serialVersionUID = -2922863246624460871L;
    static final String DESERIALIZATION_MISSING_FIELD_TEMPLATE = "Field \"%s\" is missing. Deserialization failed.";
    static final String DESERIALIZATION_MESSAGE_TEMPLATE = "Unable to deserialize object: %s";
    public static final String DEFAULT_MESSAGE = "Unable to deserialize object.";
    public static final Response.Status DEFAULT_STATUS = Response.Status.INTERNAL_SERVER_ERROR;

    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(Throwable throwable) {
        super(DEFAULT_MESSAGE, throwable);
    }

    public DeserializationException(Object object, Throwable throwable) {
        super(String.format(DESERIALIZATION_MESSAGE_TEMPLATE, object), throwable);
    }

    public DeserializationException(String message, Response.Status status, Throwable throwable) {
        super(message, status, throwable);
    }

    public static DeserializationException missingField(String field) {
        return new DeserializationException(String.format(DESERIALIZATION_MISSING_FIELD_TEMPLATE, field));
    }

    public static DeserializationException missingField(String field, Throwable throwable) {
        return new DeserializationException(String.format(DESERIALIZATION_MISSING_FIELD_TEMPLATE, field), DEFAULT_STATUS, throwable);
    }
}
