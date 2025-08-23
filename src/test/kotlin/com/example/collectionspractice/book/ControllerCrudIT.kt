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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
@AutoConfigureMockMvc
class ControllerCrudIT @Autowired constructor(
    val mockMvc: MockMvc,
    val mapper: ObjectMapper,
    val repo: BookRepository
) {
    @BeforeEach
    fun clean() = repo.deleteAll()

    @Test
    fun `POST-GET-PUT-DELETE`() {
        // POST
        val postRes = mockMvc.perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookRequest("MVC", "Tester", 9900)))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.title").value("MVC"))
            .andReturn()

        val id = mapper.readTree(postRes.response.contentAsString).get("id").asLong()

        // GET
        mockMvc.perform(get("/api/books/{id}", id))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.author").value("Tester"))

        // PUT
        mockMvc.perform(
            put("/api/books/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookRequest("MVC2", "Tester2", 12000)))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("MVC2"))
            .andExpect(jsonPath("$.price").value(12000))

        // DELETE
        mockMvc.perform(delete("/api/books/{id}", id))
            .andExpect(status().isNoContent)

        // GET 404
        mockMvc.perform(get("/api/books/{id}", id))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
    }

    @Test
    fun `validation error on POST`() {
        mockMvc.perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookRequest("", "", -1)))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
            .andExpect(jsonPath("$.details", hasSize<Int>(greaterThan(0))))
    }
}
