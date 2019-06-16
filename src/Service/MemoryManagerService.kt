package Service

import Memory.*
import Parameter.AlgorithmType

class MemoryManagerService(
    val algorithmType: AlgorithmType? = null,
    val numberProccess: Int = 0,
    val totalSeconds: Int = 0,
    val memorySize: Int = 0
) {
    var memory: Memory? = null

    init {
        val slots: List<Slot> = createSlots(memorySize)
        memory = Memory(memorySize, slots)
        runProcessing()
    }

    private fun runProcessing() {
        (0..5).forEach{
            Thread.sleep((totalSeconds * 10000).toLong())
            printMemory()
        }
    }

    private fun createSlots(size: Int): List<Slot> {
        var slots: MutableList<Slot> = mutableListOf()
        (0..size).forEach { slots.add(Slot()) }
        return slots
    }

    private fun printMemory() {
        val quantitySlotsLine = 200
        val slots = memory?.slots
        println("[_____________ MEMORY PRINTING _____________]")
        for (i in 0 until slots?.size!!) {
            val currentSlot = slots[i]
            if (i!=0 && i % quantitySlotsLine == 0) {
                println(currentSlot.simbol)
            } else {
                print(currentSlot.simbol)
            }

        }
        println("[______________ END PRINTING _______________]")
    }


}