package exercise

import com.example.collectionspractice.book.BookRepository
import com.example.collectionspractice.book.BookRequest

fun main() {
    val repo = BookRepository()
    val a = repo.save(BookRequest("Clean Code", "Robert C. Martin", 42000))
    val b = repo.save(BookRequest("Effective Java", "Joshua Bloch", 55000))

    println("All: " + repo.findAll())
    println("One: " + repo.findById(a.id))
    println("Update: " + repo.update(a.id, BookRequest("Clean Code (2nd)", "Robert C. Martin", 45000)))
    println("Delete b: " + repo.delete(b.id))
    println("All after ops: " + repo.findAll())
}