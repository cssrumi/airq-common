package pl.airq.common.process.command;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.Payload;
import pl.airq.common.process.event.AirqEvent;

@RegisterForReflection
public abstract class AirqCommand<P extends Payload> extends AirqEvent<P> {

    protected AirqCommand(Timestamp timestamp, P payload, Class<?> commandClass) {
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
