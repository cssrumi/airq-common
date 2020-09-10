package pl.airq.common.domain.process;

import com.google.common.base.CaseFormat;
import pl.airq.common.domain.process.event.Event;

public class TopicFactory {

    private TopicFactory() {
    }

    public static <E extends Event> String internal(Class<E> clazz) {
        return clazz.getSimpleName();
    }

    public static <E extends Event> String external(Class<E> clazz) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, clazz.getSimpleName());
    }
}
