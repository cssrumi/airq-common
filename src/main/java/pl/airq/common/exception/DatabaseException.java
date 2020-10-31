package pl.airq.common.exception;

public class DatabaseException extends DomainException {

    private static final long serialVersionUID = -35262461969636185L;
    public static final String DEFAULT_MESSAGE = "Database error occurred.";

    public DatabaseException() {
        super(DEFAULT_MESSAGE);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable throwable) {
        super(DEFAULT_MESSAGE, throwable);
    }
}
