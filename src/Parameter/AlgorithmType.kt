package Parameter

enum class AlgorithmType(val position: Int, val value: String) {
    FIRST_FIT(1, "first-fit"),
    CIRCULAR_FIT(2, "circular-fit"),
    BEST_FIT(3, "best-fit"),
    WORST_FIT(4, "worst-fit"),
}