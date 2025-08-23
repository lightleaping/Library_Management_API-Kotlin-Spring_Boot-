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
class ServiceCrudAndSearchTest @Autowired constructor(
    val service: BookService,
    val repo: BookRepository
) {
    @BeforeEach
    fun clean() = repo.deleteAll()

    @Test
    fun `create-getOne-replace-delete`() {
        val created = service.create(BookRequest("Service", "Tester", 2000))
        val got = service.getOne(created.id)
        assertEquals("Service", got.title)

        val replaced = service.replace(created.id, BookRequest("Service2", "Tester2", 3000))
        assertEquals("Service2", replaced.title)
        assertEquals(3000, replaced.price)

        service.delete(created.id)
        assertThrows(NotFoundException::class.java) { service.getOne(created.id) }
    }

    @Test
    fun `list with query paging and sort whitelist`() {
        repeat(15) { i -> service.create(BookRequest("Kotlin $i", "A$i", (1000 + i).toLong())) }

        // 기본 정렬 createdAt,desc
        val p0 = service.search("kotlin", 0, 10, null)
        assertEquals(10, p0.content.size)

        // 화이트리스트 정렬
        val p1 = service.search("kotlin", 0, 10, "price,asc")
        assertEquals(10, p1.content.size)

        // 잘못된 정렬 키 → IllegalArgumentException
        assertThrows(IllegalArgumentException::class.java) {
            service.search("kotlin", 0, 10, "hacker,asc")
        }
    }
}
