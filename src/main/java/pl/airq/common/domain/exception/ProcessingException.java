package pl.airq.common.domain.exception;

import javax.ws.rs.core.Response;

public class ProcessingException extends DomainException {

    private static final long serialVersionUID = -2847524176799851118L;
    public static final Response.Status DEFAULT_STATUS = Response.Status.BAD_REQUEST;
    public static final String DEFAULT_MESSAGE = "Processing error occurred.";

    public ProcessingException() {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public ProcessingException(Throwable throwable) {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS, throwable);
    }

    public ProcessingException(String message) {
        super(message, DEFAULT_STATUS);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, DEFAULT_STATUS, cause);
    }
}
