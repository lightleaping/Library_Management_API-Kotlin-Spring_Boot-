package exercise

import com.example.collectionspractice.book.*

fun main() {
    val repo = BookRepository()
    val service = BookService(repo)

    val a = service.create(BookRequest("Clean Code", "Robert C. Martin", 42000))
    println("Created: $a")

    println("List: " + service.getAll())

    val updated = service.replace(a.id, BookRequest("Clean Code (2nd)", "Robert C. Martin", 45000))
    println("Updated: $updated")

    service.delete(a.id)
    println("After delete: " + service.getAll())

    try {
        service.getOne(a.id) // should throw
    } catch (e: NotFoundException) {
        println("Expected 404-style error: ${e.message}")
    }
}