package kotlin_theory_review.exercise

data class Order(val userId: String, val amount: Double, val status: String)

fun topSpenders(orders: List<Order>): String {
    return orders
        .filter { it.status == "PAID" }
        .groupBy { it.userId }
        .mapValues { (_, list) -> list.sumOf {it.amount} }
        .toList()
        .sortedByDescending { it.second }
        .joinToString(", ") { "${it.first}: ${it.second}" }
}

fun main() {
    val orders = listOf(
        Order("U1", 30.0, "PAID"),
        Order("U2", 15.0, "REFUND"),
        Order("U1", 55.0, "PAID"),
        Order("U3", 12.0, "PAID"),
        Order("U2", 48.0, "PAID")
    )
    println(topSpenders(orders))
}