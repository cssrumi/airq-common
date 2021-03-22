package pl.airq.common.config.properties;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.spi.Converter;
import pl.airq.common.domain.notification.NotificationGroup;

public class NotificationGroupConverter implements Converter<NotificationGroup>, Serializable {

    private static final long serialVersionUID = -7451154426058647867L;

    @Override
    public NotificationGroup convert(String s) {
        return NotificationGroup.valueOf(StringUtils.upperCase(s));
    }
}
