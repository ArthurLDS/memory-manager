package Memory

data class Process(
    var pid: Int = 0,
    var size: Int = 0,
    var cicles: Int = 0,
    var currentCicles: Int = 0,
    var simbol: String = "",
    var wasExecuted: Boolean = false
) {
    fun clear() {
        this.pid = 0
        this.size = 0
        this.cicles = 0
        this.currentCicles = 0
        this.simbol = "."
    }
}
