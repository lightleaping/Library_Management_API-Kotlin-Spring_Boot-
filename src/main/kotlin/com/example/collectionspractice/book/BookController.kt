package com.example.collectionspractice.book

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(private val service: BookService) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): BookResponse = service.getOne(id)

    @PostMapping
    fun create(@Valid @RequestBody req: BookRequest): ResponseEntity<BookResponse> {
        val saved = service.create(req)
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @PutMapping("/{id}")
    fun replace(
        @PathVariable id: Long,
        @Valid @RequestBody req: BookRequest
    ): BookResponse = service.replace(id, req)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = service.delete(id)

    @GetMapping
    fun list(
        @RequestParam(required = false) query: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): List<BookResponse> = service.list(query, page, size)

}