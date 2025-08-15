package exercise

fun inStockUnderPrice(
    books: List<Map<String, Any?>>,
    limit: Double = 40.0
): List<String> {
    return books.mapNotNull { book ->
        val title = book["title"] as? String ?: return@mapNotNull null
        val price = (book["price"] as? Number)?.toDouble() ?: return@mapNotNull null
        val stock = (book["stock"] as? Number)?.toInt() ?: 0

        if (stock > 0 && price <= limit) title to price else null
    }.sortedBy { it.second }
        .map { (t, p) -> "$t - $p" }
}

fun main() {
    val books = listOf(
        mapOf("title" to "Kotlin in Action", "price" to 39.9, "stock" to 5),
        mapOf("title" to "Spring Boot Up & Running", "price" to 29.5, "stock" to 0),
        mapOf("title" to "Clean Code", "price" to 42.0, "stock" to 12)
    )
println(inStockUnderPrice(books))
}