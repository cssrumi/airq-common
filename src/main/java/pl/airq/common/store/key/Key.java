package pl.airq.common.store.key;

public interface Key {

    static final String DEFAULT_DELIMITER = "-";
    static final String EMPTY_ARG_MESSAGE_TEMPLATE = "%s %s can't be null";

    String value();
}
