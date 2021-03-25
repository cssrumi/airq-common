package pl.airq.common.infrastructure.kafka;

import com.google.common.base.Preconditions;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import pl.airq.common.infrastructure.store.key.TSKey;

public class TSKeyDeserializer implements Deserializer<TSKey> {

    private final StringDeserializer stringDeserializer = new StringDeserializer();

    @Override
    public TSKey deserialize(String topic, byte[] data) {
        Preconditions.checkNotNull(data);
        return TSKey.from(stringDeserializer.deserialize(topic, data));
    }
}
