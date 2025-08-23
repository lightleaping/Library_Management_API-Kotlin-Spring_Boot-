package com.example.collectionspractice.book

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RepoCrudTest @Autowired constructor(
    val repo: BookRepository
) {
    @BeforeEach
    fun clean() = repo.deleteAll()

    @Test
    fun `save-find-delete`() {
        val saved = repo.save(BookEntity(title = "Repo", author = "A", price = 1000))
        val found = repo.findById(saved.id!!).orElse(null)
        assertNotNull(found)
        assertEquals("Repo", found!!.title)

        repo.deleteById(saved.id!!)
        assertFalse(repo.findById(saved.id!!).isPresent)
    }

    @Test
    fun `search by title or author with paging`() {
        repeat(25) { i -> repo.save(BookEntity(null, "Kotlin $i", "Author$i", (1000 + i).toLong())) }

        val page0 = repo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            "kotlin", "kotlin", org.springframework.data.domain.PageRequest.of(0, 10)
        )
        val page1 = repo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            "kotlin", "kotlin", org.springframework.data.domain.PageRequest.of(1, 10)
        )

        assertEquals(10, page0.content.size)
        assertEquals(10, page1.content.size)
        assertTrue(page0.totalElements >= 25)
    }
}
