package kafka.uniqueAccounts.stream

import kafka.uniqueAccounts.stream.config.StreamConfig
import kafka.uniqueAccounts.stream.serialization.BlockDeserializer
import kafka.uniqueAccounts.stream.processor.UniqueAccountsProcessorSupplier
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.streams.*

fun main(args: Array<String>) {
    UniqueAccountsStream().start()
}

class UniqueAccountsStream(){
    fun start(){
        val uniqueAccountsStream = KafkaStreams(getTopology(), StreamConfig.getStreamProperties())
        uniqueAccountsStream.cleanUp()
        uniqueAccountsStream.start()
        Runtime.getRuntime().addShutdownHook(Thread(uniqueAccountsStream::close))
    }

    private fun getTopology(): Topology{
        val topology = Topology()
        topology.addSource("Ethereum-producer", StringDeserializer(), BlockDeserializer(), "ethereum_blocks")

                .addProcessor("Processor", UniqueAccountsProcessorSupplier(), "Ethereum-producer")
                .addStateStore(StreamConfig.getBlockNumberUniqueAccountsStoreSupplier(), "Processor")
                .addStateStore(StreamConfig.getSynchronizationStoreSupplier(), "Processor")
                .addSink("UniqueAccounts-stream", "uniqueAccounts", "Processor")
        return topology
    }
}
