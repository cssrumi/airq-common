package pl.airq.common.process;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.airq.common.process.command.AppCommand;
import pl.airq.common.process.event.AirqEvent;
import pl.airq.common.process.event.AppEvent;

@ApplicationScoped
public class AppEventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppEventBus.class);
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

    public <T extends AirqEvent<?>> void publish(T airqEvent) {
        LOGGER.info("Publishing AirqEvent: {}", airqEvent.eventType());
        eventBus.publish(airqEvent.eventType(), airqEvent);
    }

    public <T extends AppEvent<?>> void sendAndForget(String topic, T event) {
        eventBus.sendAndForget(topic, event);
    }

    public <T extends AirqEvent<?>> void sendAndForget(T airqEvent) {
        LOGGER.info("SendAndForget AirqEvent: {}", airqEvent.eventType());
        eventBus.sendAndForget(airqEvent.eventType(), airqEvent);
    }

    public <T extends AppEvent<?>> void sendAndForget(T event) {
        sendAndForget(event.defaultTopic(), event);
    }

}
