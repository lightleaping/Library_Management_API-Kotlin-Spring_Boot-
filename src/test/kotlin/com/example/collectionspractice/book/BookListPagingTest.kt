package com.example.collectionspractice.book

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

// src/test/kotlin/.../BookListPagingTest.kt
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 있어도 무방, 없어도 됨
class BookListPagingTest @Autowired constructor(
    val service: BookService,
    val repo: BookRepository
) {
    @Test
    fun `검색과 페이징이 동작한다`() {
        repeat(15) { i ->
            repo.save(BookEntity(title = "Kotlin $i", author = "Author$i", price = 1000L + i))
        }
        val page = service.search(query = "Kotlin", page = 0, size = 10, sort = "createdAt,desc")
        assertTrue(page.content.isNotEmpty())
        assertTrue(page.totalElements >= 15)
    }
}