package kotlin_theory_review.exercise

fun filterPrefix(words: List<String>, prefix: String = "de"): String {
    return words.filter { it.lowercase().startsWith(prefix.lowercase()) }
        .joinToString(", ")
}

fun main() {
    val words = listOf("deal", "data", "delta", "alpha", "debug")
    println("FilterWords: ${filterPrefix(words)}")
}