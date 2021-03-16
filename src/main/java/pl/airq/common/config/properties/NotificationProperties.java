package pl.airq.common.config.properties;

import io.quarkus.arc.config.ConfigProperties;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

@ConfigProperties(prefix = "notification")
public class NotificationProperties {

    @NotNull
    private Boolean enabled = true;
    @Nullable
    private RestService rest = null;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public RestService getRest() {
        return rest;
    }

    public void setRest(RestService rest) {
        this.rest = rest;
    }
}
