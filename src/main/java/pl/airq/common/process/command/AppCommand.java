package pl.airq.common.process.command;

import io.quarkus.runtime.annotations.RegisterForReflection;
import pl.airq.common.domain.vo.Timestamp;
import pl.airq.common.process.Payload;
import pl.airq.common.process.event.AppEvent;

@RegisterForReflection
public abstract class AppCommand<P extends Payload, R> extends AppEvent<P> {

    protected AppCommand(Timestamp timestamp, P payload) {
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
