import Parameter.ParametersInput
import Service.MemoryManagerService
import java.util.*

fun main(args: Array<String>) {

    val parameters = ParametersInput()
    parameters.writeHeaderParameters()

//    parameters.readAlgorithmType()
//    parameters.readNumberProccess()
//    parameters.readTotalSeconds()

    val memoryManager = MemoryManagerService(parameters.algorithmType, parameters.numberProccess, parameters.totalSeconds)
    memoryManager.createMemory(5000, 4)
}
