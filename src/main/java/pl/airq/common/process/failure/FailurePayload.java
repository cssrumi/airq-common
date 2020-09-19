package pl.airq.common.process.failure;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.process.Payload;

@RegisterForReflection
public class FailurePayload implements Payload {

    public final Throwable throwable;

    public FailurePayload(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return "FailurePayload{" +
                "throwable=" + throwable +
                '}';
    }
}
