package pl.airq.common.infrastructure.kafka;

import com.google.common.base.Preconditions;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import pl.airq.common.store.key.TSKey;

public class TSKeySerializer implements Serializer<TSKey> {

    private final StringSerializer stringSerializer = new StringSerializer();

    @Override
    public byte[] serialize(String topic, TSKey data) {
        Preconditions.checkNotNull(data);
        return stringSerializer.serialize(topic, data.value());
    }
}
