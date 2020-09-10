package pl.airq.common.domain.process;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import pl.airq.common.domain.process.command.AppCommand;
import pl.airq.common.domain.process.event.AppEvent;

@Dependent
public class AppEventBus {

    private final EventBus eventBus;

    @Inject
    public AppEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public <T extends AppCommand<?, R>, R> Uni<R> request(String topic, T command) {
        return eventBus.request(topic, command)
                       .onItem().transform(result -> ((Try<R>) result.body()).get());
    }

    public <T extends AppCommand<?, R>, R> Uni<R> request(T command) {
        return request(command.defaultTopic(), command);
    }

    public <T extends AppEvent<?>> void publish(String topic, T event) {
        eventBus.publish(topic, event);
    }

    public <T extends AppEvent<?>> void publish(T event) {
        publish(event.defaultTopic(), event);
    }

    public <T extends AppEvent<?>> void sendAndForget(String topic, T event) {
        eventBus.sendAndForget(topic, event);
    }

    public <T extends AppEvent<?>> void sendAndForget(T event) {
        sendAndForget(event.defaultTopic(), event);
    }

}
