package pl.airq.common.process.event;

import pl.airq.common.process.command.AppCommand;

public interface AppEventFactory<C extends AppCommand> {

    <E extends AppEvent> E from(C command);
}
