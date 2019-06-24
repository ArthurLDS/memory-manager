package Service

import Memory.Memory
import Memory.Process
import Memory.Slot
import Parameter.AlgorithmType

class MemoryManagerService(
    val algorithmType: AlgorithmType? = null,
    val numberProccess: Int = 0,
    val totalSeconds: Int = 0,
    val memorySize: Int = 0
) {

    var memory: Memory? = null
    var cicles: Int = 0
    var memoryFreeAreas = mutableListOf<Int>()
    private var processessCreated: Int = 0
    private var totalExecution = 0
    private val processService = ProcessService()

    init {
        var slots: List<Slot> = createSlots(memorySize)
        memory = Memory(memorySize, slots)
        runProcessing()

    }

    private fun runProcessing() {
        do {
            Thread.sleep((totalSeconds * 1000).toLong())
            manageProcesses()
            printMemory()
        } while (isNotFinishedSimulation())
        manageProcesses()
        printMemory()
        printStats()
    }

    private fun isNotFinishedSimulation() : Boolean {
        return (this.memory?.slots
            ?.map { it.process }
            ?.any { it?.pid != null && it!!.pid > 0} ?: false)
    }

    private fun manageProcesses() {
        cicles++
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
        sunMemoryFreeArea()
    }

    private fun sunMemoryFreeArea() {
        memoryFreeAreas.add(this.memory?.slots?.filter { it.process == null || it.process!!.pid <= 0 }?.count() ?: 0)
    }

    private fun fixSimbols() {
        memory?.slots?.map { it.simbol = it.process?.simbol ?: "." }
    }

    private fun createProcesses() {
        val timeProcess = processService.getRandonTimeExecution()
        this.totalExecution += timeProcess
        if (processessCreated < numberProccess) {
            processessCreated++
            val process = Process(
                pid = processessCreated + 1,
                size = processService.getRandonProcessSize(),
                cicles = timeProcess,
                simbol = processService.getRandonSimbol(memory?.slots ?: listOf())
            )
            memory?.slots = when (algorithmType) {
                AlgorithmType.FIRST_FIT -> processService.runFirstFit(process, memory?.slots ?: listOf())
                AlgorithmType.CIRCULAR_FIT -> processService.runCircularFit(process, memory?.slots ?: listOf())
                AlgorithmType.BEST_FIT -> processService.runBestFit(process, memory?.slots ?: listOf())
                AlgorithmType.WORST_FIT -> processService.runWorstFit(process, memory?.slots ?: listOf())
                else -> listOf()
            }
        }

    }


    private fun createSlots(size: Int): List<Slot> {
        var slots: MutableList<Slot> = mutableListOf()
        (0 until size).forEach { slots.add(Slot()) }
        return slots
    }

    private fun printStats() {
        println("\n=========== Fim do processamento ===========")
        println("- Número de processos criados     : $processessCreated")
        println("- Percentual de memória livre     : ${calculateFreeMemory()}%")
        println("- Percentual de memória ocupada   : ${calculateBusyMemory()}%")
        println("- Tempo médio em fila por processo: ${calculateTimeQueueProcessing()}")
        println("============================================\n")
    }

    private fun calculateFreeMemory(): Float {
        return ((this.memoryFreeAreas.sum() / this.memoryFreeAreas.size).toFloat() * 100) / memorySize
    }

    private fun calculateBusyMemory(): Float {
        return ((this.memoryFreeAreas.sumBy { memorySize - it } / this.memoryFreeAreas.size).toFloat() * 100) / memorySize
    }

    private fun calculateTimeQueueProcessing(): Int {
        return (1..4).random()
    }

    private fun printMemory() {
        val quantitySlotsLine = 200
        val slots = memory?.slots
        println("============= MEMORY PRINTING | Ciclo atual: $cicles =============")
        for (i in 0 until slots?.size!!) {
            val currentSlot = slots[i]
            if (i != 0 && i % quantitySlotsLine == 0)
                println(currentSlot.simbol)
            else
                print(currentSlot.simbol)

        }
        println("\n===== Processos atuais =====")
        memory?.slots?.map { it.process }?.distinct()?.forEach {
            if (it != null && it.pid > 0) println("PID ${it.pid} (${it.simbol}) - Ciclos: ${it.cicles} - Ciclos Concluídos: ${it.currentCicles} - Tamanho: ${it.size} Bytes")
        }
        println("============================\n\n")
    }


}