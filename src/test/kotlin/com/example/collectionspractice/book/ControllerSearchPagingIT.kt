package com.example.collectionspractice.book

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@AutoConfigureMockMvc
class ControllerSearchPagingIT @Autowired constructor(
    val mockMvc: MockMvc,
    val mapper: ObjectMapper,
    val repo: BookRepository
) {
    @BeforeEach
    fun seed() {
        repo.deleteAll()
        repeat(23) { i -> repo.save(BookEntity(null, "Kotlin $i", "Author${i%3}", (1000 + i).toLong())) }
    }

    @Test
    fun `list paging and sort`() {
        mockMvc.perform(get("/api/books").param("page", "0").param("size", "10"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content", hasSize<Any>(10)))
            .andExpect(jsonPath("$.totalElements").value(greaterThanOrEqualTo(23)))

        mockMvc.perform(get("/api/books").param("query", "kotlin").param("sort", "price,asc"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content", not(empty<Any>())))
            .andExpect(jsonPath("$.content[0].price").value(lessThanOrEqualTo(2000)))
    }

    @Test
    fun `advanced filters by authorExact and price range`() {
        mockMvc.perform(
            get("/api/books/advanced")
                .param("authorExact", "Author1")
                .param("priceMin", "1005")
                .param("priceMax", "1015")
                .param("sort", "createdAt,desc")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content", not(empty<Any>())))
            .andExpect(jsonPath("$.page").value(greaterThanOrEqualTo(0)))
    }

    @Test
    fun `invalid sort returns 400`() {
        mockMvc.perform(get("/api/books").param("sort", "hacker,down"))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
            .andExpect(jsonPath("$.message", containsString("unsupported sort")))
    }
}