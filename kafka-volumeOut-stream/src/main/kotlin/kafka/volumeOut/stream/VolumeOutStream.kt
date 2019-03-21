package kafka.volumeOut.stream

import kafka.volumeOut.stream.config.StreamConfig
import kafka.volumeOut.stream.serialization.BlockDeserializer
import kafka.volumeOut.stream.processor.VolumeOutProcessorSupplier
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.streams.*

fun main(args: Array<String>) {
    VolumeOutStream().start()
}

class VolumeOutStream(){
    fun start(){
        val volumeOutStream = KafkaStreams(getTopology(), StreamConfig.getStreamProperties())
        volumeOutStream.cleanUp()
        volumeOutStream.start()
        Runtime.getRuntime().addShutdownHook(Thread(volumeOutStream::close));
    }

    fun getTopology(): Topology{
        val topology = Topology()
        topology.addSource("Ethereum-producer", StringDeserializer(), BlockDeserializer(), "ethereum_blocks")

                .addProcessor("Processor", VolumeOutProcessorSupplier(), "Ethereum-producer")
                .addStateStore(StreamConfig.getAddressBalanceStoreSupplier(), "Processor")
                .addStateStore(StreamConfig.getBlockAddressBalanceStoreSupplier(), "Processor")
                .addStateStore(StreamConfig.getSynchronizationStoreSupplier(), "Processor")
                .addSink("VolumeOut-stream", "volumeOut", "Processor")
        return topology
    }
}