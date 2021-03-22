package pl.airq.common.config.properties;

import io.quarkus.arc.config.ConfigProperties;
import javax.validation.constraints.NotEmpty;
import pl.airq.common.domain.notification.NotificationGroup;

@ConfigProperties(prefix = "failure")
public class FailureProperties {

    private Boolean enableNotifications = true;
    private Boolean enableLogging = true;
    @NotEmpty
    private NotificationGroup[] deliverTo = {NotificationGroup.DEV, NotificationGroup.PO};

    public Boolean getEnableNotifications() {
        return enableNotifications;
    }

    public void setEnableNotifications(Boolean enableNotifications) {
        this.enableNotifications = enableNotifications;
    }

    public Boolean getEnableLogging() {
        return enableLogging;
    }

    public void setEnableLogging(Boolean enableLogging) {
        this.enableLogging = enableLogging;
    }

    public NotificationGroup[] getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(NotificationGroup[] deliverTo) {
        this.deliverTo = deliverTo;
    }
}
