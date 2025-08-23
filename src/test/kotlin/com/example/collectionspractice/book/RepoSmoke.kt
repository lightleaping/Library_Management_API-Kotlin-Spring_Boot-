// src/test/kotlin/com/example/collectionspractice/book/RepoSmoke.kt
package com.example.collectionspractice.book

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
class RepoSmoke @Autowired constructor(
    val repo: BookRepository
) {
    @Test
    fun testSaveAndFind() {
        val saved = repo.save(BookEntity(title = "JUnit Test", author = "Tester", price = 1000))
        val found = repo.findById(saved.id!!).get()
        assertEquals("JUnit Test", found.title)
    }
}
