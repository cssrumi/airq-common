package pl.airq.common.infrastructure.kafka;

import com.google.common.base.Preconditions;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import pl.airq.common.infrastructure.store.key.SKey;
import pl.airq.common.domain.vo.StationId;

public class SKeyDeserializer implements Deserializer<SKey> {

    private final StringDeserializer stringDeserializer = new StringDeserializer();

    @Override
    public SKey deserialize(String topic, byte[] data) {
        Preconditions.checkNotNull(data);
        return new SKey(StationId.from(stringDeserializer.deserialize(topic, data)));
    }
}
