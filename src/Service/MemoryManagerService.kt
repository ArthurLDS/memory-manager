package Service

import Memory.*
import Parameter.AlgorithmType

class MemoryManagerService(
    val algorithmType: AlgorithmType? = null,
    val numberProccess: Int = 0,
    val totalSeconds: Int = 0
) {

    val memory: Memory? = null

    fun createMemory(size: Int, numPartitions: Int) {
        val partitions = createPatitions(size, numPartitions)
        val memory = Memory(size, partitions)
    }

    fun createPatitions(sizeMemory: Int, numPartitions: Int): List<Partition> {
        val sizesPartitions = getSizesPartitions(sizeMemory, numPartitions)
        val partitions = mutableListOf<Partition>()

        (0 until numPartitions).forEach { partitions.add(Partition(sizesPartitions[it], mutableListOf())) }

        return partitions
    }

    fun createProcesses(quantity: Int) {

    }

    fun getSizesPartitions(sizeMemory: Int, numPartitions: Int): List<Int> {
        val totalPartition = sizeMemory / numPartitions
        val halfTotal = totalPartition / 2

        var sizes: MutableList<Int> = mutableListOf()
        (1 until numPartitions).forEach { sizes.add((halfTotal..totalPartition).random()) }
        val restMemory = sizeMemory - sizes.sum()
        sizes.add(restMemory)
        sizes.shuffle()
        return sizes
    }


}