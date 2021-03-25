package pl.airq.common.infrastructure.store.key;

public interface Key {

    String DEFAULT_DELIMITER = "-";
    String EMPTY_ARG_MESSAGE_TEMPLATE = "%s %s can't be null";

    String value();
}
