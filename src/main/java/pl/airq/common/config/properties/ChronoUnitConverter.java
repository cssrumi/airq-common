package pl.airq.common.config.properties;

import io.smallrye.config.common.AbstractConverter;
import java.time.temporal.ChronoUnit;
import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

@ApplicationScoped
public class ChronoUnitConverter extends AbstractConverter<ChronoUnit> {

    @Override
    public ChronoUnit convert(String s) {
        return ChronoUnit.valueOf(StringUtils.upperCase(s));
    }
}
