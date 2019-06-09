package Parameter

import java.util.Scanner

class ParametersInput(
    var algorithmType: AlgorithmType? = null,
    var numberProccess: Int = 0,
    var totalSeconds: Int = 0
) {

    private val scanner = Scanner(System.`in`)

    fun readAlgorithmType() {
        println(getLabelAlgorithmType())
        val algorithmSelected = scanner.nextInt()
        this.algorithmType = AlgorithmType.values().first { it.position === algorithmSelected }
    }

    fun readNumberProccess() {
        println("Informe o número de processos: ")
        this.numberProccess = scanner.nextInt()
    }

    fun readTotalSeconds() {
        println("Informe a quantidade segundos: ")
        this.totalSeconds = scanner.nextInt()
    }

    fun writeHeaderParameters() {
        println("====================================")
        println("====== GERENCIADOR DE MEMÓRIA ======")
        println("====================================\n")
    }

    private fun getLabelAlgorithmType(): String {
        var label = "Selecione o tipo de algoritmo de escalonamento: "
        AlgorithmType.values().forEach { label += "\n${it.position}.${it.value}" }
        return label
    }
}