package kotlin_theory_review.exercise

fun sortMapByValue(m: Map<String, Int>): String {
    return m.toList()
        .sortedBy { it.second }
        .joinToString(", ") { "${it.first}:${it.second}" }
}

fun main() {
    val sales = mapOf("Jan" to 120, "Feb" to 95, "Mar" to 180, "Apr" to 150)
    println("Sorted sales: ${sortMapByValue(sales)}")
}