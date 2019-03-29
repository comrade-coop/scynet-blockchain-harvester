package kafka.balanceLastSeen.distribution.serialization

import kafka.balanceLastSeen.distribution.messages.StreamJoin
import org.apache.kafka.common.serialization.Deserializer

class JoinDeserializer: Deserializer<StreamJoin.Join> {
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
    }

    override fun deserialize(topic: String?, data: ByteArray?): StreamJoin.Join {
        return StreamJoin.Join.parseFrom(data)
    }

    override fun close() {
    }
}