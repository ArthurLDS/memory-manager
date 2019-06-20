package Service

import Memory.*
import Parameter.AlgorithmType
import java.util.*

class MemoryManagerService(
    val algorithmType: AlgorithmType? = null,
    val numberProccess: Int = 0,
    val totalSeconds: Int = 0,
    val memorySize: Int = 0
) {

    var memory: Memory? = null
    var processessCreated: Int = 0
    var cicles: Int = 0
    private val scanner = Scanner(System.`in`)

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
        var resp = ""
        do {
            Thread.sleep((totalSeconds * 1000).toLong())
            manageProcesses()
            printMemory()
        } while (resp != "c")
    }

    private fun manageProcesses() {
        memory?.slots?.map { it.process }?.distinct()?.map {
            if (it != null) {
                if (it.cicles > it.currentCicles)
                    it.currentCicles++
                else
                    it.clear()
            }
        }
        fixSimbols()
        createProcesses()
    }

    private fun fixSimbols() {
        memory?.slots?.map { it.simbol = it.process?.simbol ?: "." }
    }

    private fun createProcesses() {
        if (processessCreated < numberProccess) {
            processessCreated++
            val process = Process(
                pid = processessCreated + 1,
                size = ProcessService.getRandonProcessSize(),
                cicles = ProcessService.getRandonTimeExecution(),
                simbol = ProcessService.getRandonSimbol(memory?.slots ?: listOf())
            )
            memory?.slots = ProcessService.runFirstFit(process, memory?.slots ?: listOf())
        }

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
            if (i != 0 && i % quantitySlotsLine == 0)
                println(currentSlot.simbol)
            else
                print(currentSlot.simbol)

        }
        println("\n[______________ END PRINTING _______________]")
        println("")
        memory?.slots?.map { it.process }?.distinct()?.forEach {
            if(it !=null && it.pid > 0) println("PID ${it.pid} (${it.simbol}) - CICLES: ${it.cicles} - CICLES DONE: ${it.currentCicles} - SIZE: ${it.size} Bytes")
        }
        println("")
    }


}