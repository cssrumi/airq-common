package pl.airq.common.config.properties;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "failure")
public class FailureProperties {

    private Boolean enableNotifications = true;
    private Boolean enableLogging = true;

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
}
