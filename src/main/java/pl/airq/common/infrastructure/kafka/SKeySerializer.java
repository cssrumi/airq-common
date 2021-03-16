package pl.airq.common.infrastructure.kafka;

import com.google.common.base.Preconditions;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import pl.airq.common.store.key.SKey;

public class SKeySerializer implements Serializer<SKey> {

    private final StringSerializer stringSerializer = new StringSerializer();

    @Override
    public byte[] serialize(String topic, SKey data) {
        Preconditions.checkNotNull(data);
        return stringSerializer.serialize(topic, data.value());
    }
}
