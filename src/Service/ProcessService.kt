package Service

import Memory.Process
import Memory.Slot

object ProcessService {

    fun runFirstFit(process: Process, slots: List<Slot>): List<Slot> {

        val sizeProcess = process.size
        var sizeFreeArea = 0
        var indexEndFreeArea = 0
        var foundEnd = false

        for (i in 0 until slots.size) {
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
            if (currentSlot.process == null) {
                sizeFreeArea++
                indexEndFreeArea = i
            } else {
                foundEnd = true
            }
        }

        val indexFirstFreeArea = indexEndFreeArea - (sizeFreeArea - 1)
        println("FIRST SPACE FREE: $indexFirstFreeArea")
        println("LAST SPACE FREE : $indexEndFreeArea")
        println("SIZE SPACE FREE : $sizeFreeArea")

        if (sizeFreeArea >= sizeProcess) {
            (indexFirstFreeArea..indexFirstFreeArea + sizeProcess).forEach {
                slots[it].simbol = process.simbol
                slots[it].process = process
            }
        }
        return slots
    }

    fun getRandonSimbol(slots: List<Slot>): String {
        val simbols = charArrayOf('!','#','&','*','%','$','@','-','+','?','^','`','´',':',',','<','>','\'','/','A','B','C','D','E')
        return simbols[(0 until simbols.size).random()].toString()
    }

    fun getRandonProcessSize(): Int{
        return (100..1000).random()
    }
}