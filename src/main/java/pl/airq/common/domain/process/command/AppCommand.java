package pl.airq.common.domain.process.command;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.process.Payload;
import pl.airq.common.domain.process.event.AirqEvent;
import pl.airq.common.domain.process.event.AppEvent;

@RegisterForReflection
public abstract class AppCommand<P extends Payload, R> extends AppEvent<P> {

    protected AppCommand(OffsetDateTime timestamp, P payload) {
        super(timestamp, payload);
    }

    protected AppCommand(P payload) {
        super(payload);
    }

    public abstract Class<R> responseType();

    @Override
    public String toString() {
        return "Command{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                ", responseType='" + responseType() + '\'' +
                '}';
    }
}
