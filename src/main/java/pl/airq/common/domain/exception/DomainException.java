package pl.airq.common.domain.exception;

import java.io.Serializable;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class DomainException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 466149293985649359L;
    public static final String DEFAULT_MESSAGE = "Server Exception occurred";
    public static final Response.Status DEFAULT_STATUS = Response.Status.BAD_REQUEST;

    private final Response.Status status;

    public DomainException(String message, Response.Status status, Throwable throwable) {
        super(message, throwable);
        this.status = status;
    }

    public DomainException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public DomainException() {
        this(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public DomainException(Throwable throwable) {
        this(DEFAULT_MESSAGE, DEFAULT_STATUS, throwable);
    }

    public DomainException(String message) {
        this(message, DEFAULT_STATUS);
    }

    public DomainException(String message, Throwable throwable) {
        this(message, DEFAULT_STATUS, throwable);
    }

    public DomainException(String message, int code) {
        this(message, Response.Status.fromStatusCode(code));
    }

    public Response.Status getStatus() {
        return status;
    }
}
