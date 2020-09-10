package pl.airq.common.domain.process.command;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.time.OffsetDateTime;
import pl.airq.common.domain.process.Payload;
import pl.airq.common.domain.process.event.AirqEvent;

@RegisterForReflection
public abstract class AirqCommand<P extends Payload> extends AirqEvent<P> {

    protected AirqCommand(OffsetDateTime timestamp, P payload, Class<?> commandClass) {
        super(timestamp, payload, commandClass);
    }

    @Override
    public String toString() {
        return "Command{" +
                "timestamp=" + timestamp +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
