package exercise

fun averageScore(scores: List<Int>): String {
    val avg = scores.average() // built-in average function
    return String.format("%.1f", avg) // keep one decimal
}

fun main() {
    val scores = listOf(88, 92, 76, 81, 95)
    println("Average score: ${averageScore(scores)}")
}