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
        var slots: List<Slot> = createSlots(memorySize)
        memory = Memory(memorySize, slots)

//        slots[0].process = Process()
//        slots[1].process = Process()
//        slots[2].process = Process()
//        slots[3].process = Process()
//
//        slots[4996].process = Process()
//        slots[4997].process = Process()
//        slots[4998].process = Process()
//        slots[4999].process = Process()

        runProcessing()

    }

    private fun runProcessing() {
        (0..10).forEach{
            Thread.sleep((totalSeconds * 1000).toLong())
            createProcesses()
            printMemory()
        }
    }

    private fun createProcesses(){
        val processRunning : List<Process?> = memory?.slots?.map { it.process } ?: listOf()
        val process = Process(processRunning.size + 1, ProcessService.getRandonProcessSize(), 1, ProcessService.getRandonSimbol(memory?.slots ?: listOf()))
        memory?.slots = ProcessService.runFirstFit(process, memory?.slots ?: listOf())

    }


    private fun createSlots(size: Int): List<Slot> {
        var slots: MutableList<Slot> = mutableListOf()
        (0 until size).forEach { slots.add(Slot()) }
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
        println("\n[______________ END PRINTING _______________]")
    }


}