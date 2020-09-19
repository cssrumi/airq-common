package pl.airq.common.process.failure;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.process.event.AppEvent;

@RegisterForReflection
public class Failure extends AppEvent<FailurePayload> {

    public static final String DEFAULT_FAILURE_TOPIC = "Failure";

    public Failure(FailurePayload payload) {
        super(payload);
    }

    @Override
    public String defaultTopic() {
        return DEFAULT_FAILURE_TOPIC;
    }

    @Override
    public String toString() {
        return "Failure{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                '}';
    }

    public static Failure from(Throwable throwable) {
        return new Failure(new FailurePayload(throwable));
    }
}
