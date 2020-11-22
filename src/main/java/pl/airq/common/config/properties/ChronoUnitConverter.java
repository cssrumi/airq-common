package pl.airq.common.config.properties;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.spi.Converter;

public class ChronoUnitConverter implements Converter<ChronoUnit>, Serializable {

    private static final long serialVersionUID = -2222282677326862952L;

    @Override
    public ChronoUnit convert(String s) {
        return ChronoUnit.valueOf(StringUtils.upperCase(s));
    }
}
