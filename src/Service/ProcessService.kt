package Service

import Memory.FreeArea
import Memory.Process
import Memory.Slot

class ProcessService {

    private val listProcess = mutableListOf<Process>()

    fun runBestFit(process: Process, slots: List<Slot>): List<Slot> {
        listProcess.add(process)
        val ableProcess = getAbleProcess()
        var bestArea =
            getFreeAraes(ableProcess, slots).filter { it.indexStart + ableProcess.size < slots.size }.minBy { it.size }
                ?: FreeArea()
        return alocateProcess(ableProcess, slots, bestArea)
    }

    fun runWorstFit(process: Process, slots: List<Slot>): List<Slot> {
        listProcess.add(process)
        val ableProcess = getAbleProcess()
        var bestArea = getFreeAraes(ableProcess, slots).maxBy { it.size } ?: FreeArea()
        return alocateProcess(ableProcess, slots, bestArea)
    }

    fun runFirstFit(process: Process, slots: List<Slot>): List<Slot> {
        listProcess.add(process)
        val ableProcess = getAbleProcess()
        var bestArea = getFreeAraes(ableProcess, slots).first()
        return alocateProcess(ableProcess, slots, bestArea)
    }

    fun runCircularFit(process: Process, slots: List<Slot>): List<Slot> {
        listProcess.add(process)
        val ableProcess = getAbleProcess()
        var lastSlotAlocate = slots.indexOfLast { it.process?.pid ?: 0 > 0 }
        var foundArea = getFreeAraes(ableProcess, slots).first { it.indexStart > lastSlotAlocate }
        var bestArea = if (foundArea.size >= ableProcess.size) foundArea else getFreeAraes(ableProcess, slots).first()
        return alocateProcess(ableProcess, slots, bestArea)
    }

    private fun getAbleProcess(): Process {
        return listProcess.first { !it.wasExecuted }
    }

    private fun findFirstFreeArea(process: Process, slots: List<Slot>, startSearch: Int = 0): FreeArea {
        val sizeProcess = process.size
        var sizeFreeArea = 0
        var indexEndFreeArea = 0
        var foundEnd = false

        for (i in startSearch until slots.size) {
            val currentSlot = slots[i]
            if (foundEnd) {
                if (sizeFreeArea < sizeProcess) {
                    sizeFreeArea = 0
                    indexEndFreeArea = 0
                    foundEnd = false
                } else {
                    break
                }
            }
            if (currentSlot.process == null || currentSlot.process!!.pid == 0) {
                sizeFreeArea++
                indexEndFreeArea = i
            } else {
                foundEnd = true
            }
        }
        return FreeArea(sizeFreeArea, indexEndFreeArea, indexEndFreeArea - (sizeFreeArea - 1))
    }

    private fun getFreeAraes(process: Process, slots: List<Slot>): List<FreeArea> {
        var listFreeArea = mutableListOf<FreeArea>()
        var lastFreeArea = FreeArea()
        do {
            lastFreeArea = findFirstFreeArea(process, slots, lastFreeArea.indexEnd)
            listFreeArea.add(lastFreeArea)
        } while (lastFreeArea.indexEnd < slots.size - 1)
        return listFreeArea
    }

    private fun alocateProcess(process: Process, slots: List<Slot>, bestArea: FreeArea): List<Slot> {
        val limit = bestArea.indexStart + process.size
        if (limit < slots.size) {
            this.listProcess.map { if (it.pid == process.pid) it.wasExecuted = true }
            (bestArea.indexStart..limit).forEach {
                slots[it].simbol = process.simbol
                slots[it].process = process
            }
        }
        return slots
    }

    fun getRandonTimeExecution(): Int {
        return (1..10).random()
    }

    fun getRandonSimbol(slots: List<Slot>): String {
        val simbols = charArrayOf(
            '*',
            '!',
            '#',
            '&',
            '%',
            '$',
            '@',
            '-',
            '+',
            '?',
            '^',
            '`',
            '´',
            ':',
            ',',
            '<',
            '>',
            '\'',
            '/',
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z'
        )
        var randomSimbol: String
        do {
            randomSimbol = simbols[(0 until simbols.size).random()].toString()
        } while (slots.any { it.simbol == randomSimbol })
        return randomSimbol
    }

    fun isAbleToCreateProccess(): Boolean {
        //20% chance to crate proccess
        return (1..10).random() <= 2
    }

    fun getRandonProcessSize(): Int {
        return (100..1000).random()
    }
}