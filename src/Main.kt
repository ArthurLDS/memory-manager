import Parameter.ParametersInput
import Service.MemoryManagerService

fun main(args: Array<String>) {

    val parameters = ParametersInput()
    parameters.writeHeaderParameters()

    parameters.readAlgorithmType()
    parameters.readNumberProccess()
    parameters.readTotalSeconds()

    MemoryManagerService(parameters.algorithmType, parameters.numberProccess, parameters.totalSeconds, 5000)
}
