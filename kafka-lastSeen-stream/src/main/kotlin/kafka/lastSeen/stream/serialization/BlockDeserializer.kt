package kafka.lastSeen.stream.serialization

import kafka.lastSeen.stream.messages.Messages.Block
import org.apache.kafka.common.serialization.Deserializer;

class BlockDeserializer : Deserializer<Block> {

    override fun deserialize(topic: String?, data: ByteArray?): Block {
        return Block.parseFrom(data)
    }

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
    }

    override fun close() {
    }
}