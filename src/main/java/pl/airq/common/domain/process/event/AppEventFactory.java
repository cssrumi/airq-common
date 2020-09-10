package pl.airq.common.domain.process.event;

import pl.airq.common.domain.process.command.AppCommand;

public interface AppEventFactory<C extends AppCommand> {

    <E extends AppEvent> E from(C command);
}
