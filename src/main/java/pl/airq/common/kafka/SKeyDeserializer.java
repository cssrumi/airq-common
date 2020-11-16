package pl.airq.common.kafka;

import com.google.common.base.Preconditions;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import pl.airq.common.store.key.SKey;
import pl.airq.common.store.key.TSKey;
import pl.airq.common.vo.StationId;

public class SKeyDeserializer implements Deserializer<SKey> {

    private final StringDeserializer stringDeserializer = new StringDeserializer();

    @Override
    public SKey deserialize(String topic, byte[] data) {
        Preconditions.checkNotNull(data);
        return new SKey(StationId.from(stringDeserializer.deserialize(topic, data)));
    }
}
