// src/test/kotlin/com/example/collectionspractice/book/ServiceSmoke.kt
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
class ServiceSmoke @Autowired constructor(
    val service: BookService
) {
    @Test
    fun testCreateAndGetOne() {
        val saved = service.create(BookRequest(title = "Service Test", author = "Tester", price = 2000))
        val found = service.getOne(saved.id)
        assertEquals("Service Test", found.title)
    }
}
