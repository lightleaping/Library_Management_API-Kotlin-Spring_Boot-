package com.example.collectionspractice.book

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BookService(private val repo: BookRepository) {

    fun getAll(): List<BookResponse> =
        repo.findAll().map { it.toDto() }

    fun getOne(id: Long): BookResponse =
        repo.findById(id).orElseThrow { NotFoundException("Book $id not found") }.toDto()

    fun create(req: BookRequest): BookResponse =
        repo.save(req.toEntity()).toDto()

    fun replace(id: Long, req: BookRequest): BookResponse {
        val e = repo.findById(id).orElseThrow { NotFoundException("Book $id not found") }
        e.title = req.title
        e.author = req.author
        e.price = req.price
        return repo.save(e).toDto()
    }

    fun delete(id: Long) {
        if (!repo.existsById(id)) throw NotFoundException("Book $id not found")
        repo.deleteById(id)
    }

    fun list(query: String?, page: Int, size: Int): List<BookResponse> {
        require(page >= 0) { "page must be >= 0" }
        require(size > 0) { "size must be > 0" }

        if (query.isNullOrBlank()) {
            return repo.findAll(PageRequest.of(page, size)).content.map { it.toDto() }
        }
        val matched = (repo.findByTitleContainingIgnoreCase(query) +
                repo.findByAuthorContainingIgnoreCase(query)).distinct()
        if (matched.isEmpty()) return emptyList()
        val from = page * size
        if (from >= matched.size) return emptyList()
        val to = minOf(from + size, matched.size)
        return matched.subList(from, to).map { it.toDto() }
    }
}