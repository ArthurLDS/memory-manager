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
            if (currentSlot.process == null || currentSlot.process!!.pid == 0) {
                sizeFreeArea++
                indexEndFreeArea = i
            } else {
                foundEnd = true
            }
        }

        val indexFirstFreeArea = indexEndFreeArea - (sizeFreeArea - 1)
//        println("FIRST SPACE FREE: $indexFirstFreeArea")
//        println("LAST SPACE FREE : $indexEndFreeArea")
//        println("SIZE SPACE FREE : $sizeFreeArea")
//        println("PROCCESS SIMBOL: ${process.simbol}")
//        println("CICLES:          ${process.cicles}")
//        println("CURRENT CICLES:  ${process.currentCicles}")

        if (sizeFreeArea >= sizeProcess) {
            (indexFirstFreeArea..indexFirstFreeArea + sizeProcess).forEach {
                slots[it].simbol = process.simbol
                slots[it].process = process
            }
        }
        return slots
    }


    fun getRandonTimeExecution(): Int{
        return (1..10).random()
    }

    fun getRandonSimbol(slots: List<Slot>): String {
        val simbols = charArrayOf('!','#','&','*','%','$','@','-','+','?','^','`','´',':',',','<','>','\'','/','0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z')
        var randomSimbol: String
        do {
            randomSimbol = simbols[(0 until simbols.size).random()].toString()
        } while(slots.any{it.simbol == randomSimbol})
        return randomSimbol
    }

    fun getRandonProcessSize(): Int{
        return (100..1000).random()
    }
}